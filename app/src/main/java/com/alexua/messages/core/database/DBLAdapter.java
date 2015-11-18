package com.alexua.messages.core.database;

import android.database.Cursor;

import com.alexua.messages.core.AppLog;
import com.alexua.messages.core.database.datas.DBStorable;
import com.alexua.messages.core.database.datas.Message;
import com.alexua.messages.core.database.listener.DBDeleteListener;
import com.alexua.messages.core.database.listener.DBInsertListener;
import com.alexua.messages.core.database.listener.DBUpdateListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created with IntelliJ IDEA.
 * User: Oleksii Khom
 * Date: 01.09.13
 * Time: 20:35
 * To change this template use File | Settings | File Templates.
 */
public class DBLAdapter extends Database {

    public static final String TAG = DBLAdapter.class.getCanonicalName();
    private DBAdapter dbAdapter;

    public DBLAdapter(DBAdapter dbAdapter) {
        this.dbAdapter = dbAdapter;
    }

    @Override
    public boolean open() {
        return dbAdapter.open();
    }

    @Override
    public void close() {
        dbAdapter.close();
    }

    @Override
    public long insertNewDBStorable(DBStorable newDBStorable) {
        long temp = dbAdapter.insertNewDBStorable(newDBStorable);
        final DBStorable tempData = newDBStorable;
        service.submit(new Runnable() {
            public void run() {
                synchronized (insertListeners) {
                    for (DBInsertListener listener : insertListeners.get(tempData.getTypeID())) {
                        final DBInsertListener tempListener = listener;
                        service.submit(new Runnable() {
                            public void run() {
                                try {
                                    tempListener.inserting((DBStorable) (tempData.clone()));
                                } catch (CloneNotSupportedException e) {
                                    AppLog.E(TAG, e);
                                }
                            }
                        });
                    }
                }
            }
        });
        return temp;
    }

    @Override
    public List<Long> insertNewDBStorables(List<DBStorable> listDBStorable) {
        if (listDBStorable.isEmpty()) return new ArrayList<Long>();
        final int typeID = listDBStorable.get(0).getTypeID();
        List<Long> temp = dbAdapter.insertNewDBStorables(listDBStorable);
        final List<DBStorable> tempData = listDBStorable;
        service.submit(new Runnable() {
            public void run() {
                synchronized (insertListeners) {
                    for (DBInsertListener listener : insertListeners.get(typeID)) {
                        final DBInsertListener tempListener = listener;
                        service.submit(new Runnable() {
                            public void run() {
                                tempListener.insertingList(new ArrayList<DBStorable>(tempData));
                            }
                        });
                    }
                }
            }
        });
        return temp;
    }

    @Override
    public int deleteDBStorableByType(int typeID, long rowID) {
        final int tempTypeID = typeID;
        final long tempRowID = rowID;
        int result = dbAdapter.deleteDBStorableByType(typeID, rowID);
        service.submit(new Runnable() {
            public void run() {
                synchronized (deleteListeners) {
                    for (DBDeleteListener listener : deleteListeners.get(tempTypeID)) {
                        final DBDeleteListener tempListener = listener;
                        service.submit(new Runnable() {
                            public void run() {
                                tempListener.deleted(tempTypeID, tempRowID);
                            }
                        });
                    }
                }
            }
        });
        return result;
    }

    @Override
    public int deleteDBStorable(DBStorable data) {
        final DBStorable tempData = data;
        int result = dbAdapter.deleteDBStorable(data);
        service.submit(new Runnable() {
            public void run() {
                synchronized (deleteListeners) {
                    for (DBDeleteListener listener : deleteListeners.get(tempData.getTypeID())) {
                        final DBDeleteListener tempListener = listener;
                        service.submit(new Runnable() {
                            public void run() {
                                tempListener.deleted(tempData);
                            }
                        });
                    }
                }
            }
        });
        return result;
    }

    @Override
    public boolean updateDBStorable(DBStorable data) {
        boolean result = dbAdapter.updateDBStorable(data);
        final DBStorable tempData = data;
        service.submit(new Runnable() {
            public void run() {
                synchronized (updateListeners) {
                    for (DBUpdateListener listener : updateListeners.get(tempData.getTypeID())) {
                        final DBUpdateListener tempListener = listener;
                        service.submit(new Runnable() {
                            public void run() {
                                tempListener.updateListener(tempData);
                            }
                        });
                    }
                }
            }
        });
        return result;
    }

    @Override
    public Cursor selectAllDBStorablesByType(int typeId) {
        return dbAdapter.selectAllDBStorablesByType(typeId);
    }

    @Override
    public DBStorable selectDBStorableByTypeAndId(int typeId, long dbstorableId) {
        return dbAdapter.selectDBStorableByTypeAndId(typeId, dbstorableId);
    }

    @Override
    public DBStorable parseFromCursor(Cursor c, int typeId) {
        return dbAdapter.parseFromCursor(c, typeId);
    }

    @Override
    public void addInsertListener(int typeID, DBInsertListener listener) {
        synchronized (insertListeners) {
            insertListeners.get(typeID).add(listener);
        }
    }

    @Override
    public void deleteInsertListener(DBInsertListener listener) {
        synchronized (insertListeners) {
            Iterator<Integer> keyType = insertListeners.keySet().iterator();
            while (keyType.hasNext()) {
                insertListeners.get(keyType.next()).remove(listener);
            }
        }
    }

    @Override
    public void addDeleteListener(int typeID, DBDeleteListener listener) {
        synchronized (deleteListeners) {
            deleteListeners.get(typeID).add(listener);
        }
    }


    @Override
    public void deleteDeleteListner(DBDeleteListener listener) {
        synchronized (deleteListeners) {
            Iterator<Integer> keyType = deleteListeners.keySet().iterator();
            while (keyType.hasNext()) {
                deleteListeners.get(keyType.next()).remove(listener);
            }
        }
    }


    @Override
    public void addUpdateListener(int typeID, DBUpdateListener listener) {
        synchronized (updateListeners) {
            updateListeners.get(typeID).add(listener);
        }
    }


    @Override
    public void deleteUpdateListner(DBUpdateListener listener) {
        synchronized (updateListeners) {
            Iterator<Integer> keyType = updateListeners.keySet().iterator();
            while (keyType.hasNext()) {
                updateListeners.get(keyType.next()).remove(listener);
            }
        }
    }


    private static ExecutorService service = Executors.newCachedThreadPool();
    private static HashMap<Integer, List<DBInsertListener>> insertListeners = new HashMap<Integer, List<DBInsertListener>>();
    private static HashMap<Integer, List<DBDeleteListener>> deleteListeners = new HashMap<Integer, List<DBDeleteListener>>();
    private static HashMap<Integer, List<DBUpdateListener>> updateListeners = new HashMap<Integer, List<DBUpdateListener>>();
    private static int[] typeIDs = new int[]{
            Message.TypeID
    };

    static {
        for (int typeID : typeIDs) {
            insertListeners.put(new Integer(typeID), new ArrayList<DBInsertListener>());
            deleteListeners.put(new Integer(typeID), new ArrayList<DBDeleteListener>());
            updateListeners.put(new Integer(typeID), new ArrayList<DBUpdateListener>());
        }
    }


}
