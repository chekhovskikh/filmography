SELECT  ID,

        NAME,

        LEVEL AS lvl,

        SYS_CONNECT_BY_PATH(Name, '/') AS Name_OF_Parent

        FROM GENRE

START WITH PARENT_ID is NULL

CONNECT BY PARENT_ID = PRIOR ID;
