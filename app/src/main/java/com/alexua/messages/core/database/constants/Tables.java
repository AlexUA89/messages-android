package com.alexua.messages.core.database.constants;

/**
 * Created with IntelliJ IDEA.
 * User: Oleksii Khom
 * Date: 29.08.13
 * Time: 16:57
 * To change this template use File | Settings | File Templates.
 */
public final class Tables {

    public static interface Table {
        public static final String ID = "_id";
    }

    public static final class EVENTS_TABLE implements Table {
        public static String TABLE_NAME = "events_table";
        public static String EVENT_NAME = "name";
        public static String DESCRIPTION = "description";
        public static String IMAGE_URI = "image_uri";
        public static String XCOORD = "xcoord";
        public static String YCOORD = "ycoord";
        public static String TIME_START = "time_start";
        public static String TIME_END = "time_end";
        public static String USER_ID = "user_id";
        public static String CATEGORY = "category";
    }

    public static final class FRIENDS implements Table {
        public static String TABLE_NAME = "friends_table";
        public static String ID = "friend_id";
        public static String NAME = "name";
        public static String EMAIL = "email";
    }

    public static final class MESSAGES implements Table {
        public static String TABLE_NAME = "messages_table";
        public static String MESSAGE = "message";
        public static String XCOORD = "xcoord";
        public static String YCOORD = "ycoord";
        public static String TO_USER_ID = "to_user_id";
        public static String CHAT_GROUP_ID = "chat_group_id";
        public static String USER_ID = "user_id";
        public static String USER_NAME = "user_name";
        public static String TIME = "time";
    }

    public static final class GROUPS implements Table {
        public static String TABLE_NAME = "groups_table";
        public static String NAME = "name";
    }

}
