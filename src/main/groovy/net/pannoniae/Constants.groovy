package net.pannoniae

class Constants {


    static String dropRegion = """
DROP TABLE IF EXISTS REGION
"""
    static String dropNation = """
DROP TABLE IF EXISTS NATION
"""
    static String dropSupplier = """
DROP TABLE IF EXISTS SUPPLIER
"""
    static String dropPart = """
DROP TABLE IF EXISTS PART
"""
    static String dropCustomer = """
DROP TABLE IF EXISTS CUSTOMER
"""
    static String dropPartSupp = """
DROP TABLE IF EXISTS PARTSUPP
"""
    static String dropOrders = """
DROP TABLE IF EXISTS ORDERS
"""
    static String dropLineItem = """
DROP TABLE IF EXISTS LINEITEM
"""


    static String createRegion = """
CREATE TABLE If NOT EXISTS REGION (
   R_REGIONKEY          integer              not null,
   R_NAME               char(25)             null,
   R_COMMENT            varchar(152)         null,
   constraint PK_REGION primary key (R_REGIONKEY)
)
"""

    static String createNation = """
CREATE TABLE If NOT EXISTS NATION (
  N_NATIONKEY  INTEGER not null,
  N_NAME       CHAR(25) NOT NULL,
  N_REGIONKEY  INTEGER NOT NULL,
  N_COMMENT    VARCHAR(152),
   constraint PK_NATION primary key (N_NATIONKEY),
   constraint FK_REGION foreign key (N_REGIONKEY)
      references REGION (R_REGIONKEY)  
);
"""

    static String createCustomer = """
create table CUSTOMER (
   C_CUSTKEY            integer          not null,
   C_NAME               varchar(25)          null,
   C_ADDRESS            varchar(40)          null,
   C_NATIONKEY          integer          null,
   C_PHONE              char(15)             null,
   C_ACCTBAL            decimal(12,2)        null,
   C_MKTSEGMENT         char(10)             null,
   C_COMMENT            varchar(117)         null,
   constraint PK_CUSTOMER primary key (C_CUSTKEY),
   constraint FK_CUSTOMER_NATION foreign key (C_NATIONKEY)
      references NATION (N_NATIONKEY)
)
"""

    static String createSupplier = """
create table SUPPLIER (
   S_SUPPKEY            integer          not null,
   S_NAME               char(25)             null,
   S_ADDRESS            varchar(40)          null,
   S_NATIONKEY          integer              null,
   S_PHONE              char(15)             null,
   S_ACCTBAL            real                 null,
   S_COMMENT            varchar(101)         null,
   constraint PK_SUPPLIER primary key (S_SUPPKEY),
   constraint FK_SUPPLIER_NATION foreign key (S_NATIONKEY)
      references NATION (N_NATIONKEY)
)
"""

    static String createPart = """
create table PART (
   P_PARTKEY            integer          not null,
   P_NAME               varchar(55)          null,
   P_MFGR               char(25)             null,
   P_BRAND              char(10)             null,
   P_TYPE               varchar(25)          null,
   P_SIZE               decimal(10)          null,
   P_CONTAINER          char(10)             null,
   P_RETAILPRICE        real        null,
   P_COMMENT            varchar(23)          null,
   constraint PK_PART primary key (P_PARTKEY)
)
"""

    static String createPartSupp = """
create table PARTSUPP (
   PS_PARTKEY           integer          not null,
   PS_SUPPKEY           integer          not null,
   PS_AVAILQTY          integer          null,
   PS_SUPPLYCOST        real       null,
   PS_COMMENT           varchar(199)         null,
   constraint PK_PARTSUPP primary key (PS_PARTKEY, PS_SUPPKEY),
   constraint FK_PART foreign key (PS_PARTKEY)
      references PART (P_PARTKEY),
   constraint FK_SUPP foreign key (PS_SUPPKEY)
      references SUPPLIER (S_SUPPKEY)
)
"""

    static String createOrders = """
create table ORDERS (
   O_ORDERKEY           integer          not null,
   O_CUSTKEY            integer          null,
   O_ORDERSTATUS        char(1)              null,
   O_TOTALPRICE         real               null,
   O_ORDERDATE          text             null,
   O_ORDERPRIORITY      char(15)             null,
   O_CLERK              char(15)             null,
   O_SHIPPRIORITY       integer          null,
   O_COMMENT            varchar(79)          null,
   constraint PK_ORDERS primary key (O_ORDERKEY),
   constraint FK_CUSTOMER foreign key (O_CUSTKEY)
      references CUSTOMER (C_CUSTKEY)
)
"""

    static String createLineItem = """
create table LINEITEM (
   L_ORDERKEY           integer          not null,
   L_PARTKEY            integer          null,
   L_SUPPKEY            integer         null,
   L_LINENUMBER         integer          not null,
   L_QUANTITY           real        null,
   L_EXTENDEDPRICE      real        null,
   L_DISCOUNT           real        null,
   L_TAX                real        null,
   L_RETURNFLAG         char(1)              null,
   L_LINESTATUS         char(1)              null,
   L_SHIPDATE           text             null,
   L_COMMITDATE         text             null,
   L_RECEIPTDATE        text             null,
   L_SHIPINSTRUCT       char(25)             null,
   L_SHIPMODE           char(10)             null,
   L_COMMENT            varchar(44)          null,
   constraint PK_LINEITEM primary key (L_ORDERKEY, L_LINENUMBER),
   constraint FK_ORDER foreign key (L_ORDERKEY)
      references ORDERS (O_ORDERKEY),
   constraint FK_PARTSUPP foreign key (L_PARTKEY, L_SUPPKEY)
      references PARTSUPP (PS_PARTKEY, PS_SUPPKEY)
)
"""

    static String insertRegion = """
  insert into REGION values (?,?,?)
"""
    static String insertNation = """
  insert into NATION values (?,?,?,?)
"""
    static String insertSupplier = """
  insert into SUPPLIER values (?,?,?,?,?,?,?)
"""
    static String insertPart = """
  insert into PART values (?,?,?,?,?,?,?,?,?)
"""
    static String insertCustomer = """
  insert into CUSTOMER values (?,?,?,?,?,?,?,?)
"""
    static String insertPartSupp = """
  insert into PARTSUPP values (?,?,?,?,?)
"""
    static String insertOrders = """
  insert into ORDERS values (?,?,?,?,?,?,?,?,?)
"""
    static String insertLineItem = """
  insert into LINEITEM values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
"""

    static var tables = ["region"  : new Table(createRegion, dropRegion, insertRegion),
                         "nation"  : new Table(createNation, dropNation, insertNation),
                         "supplier": new Table(createSupplier, dropSupplier, insertSupplier),
                         "part"    : new Table(createPart, dropPart, insertPart),
                         "customer": new Table(createCustomer, dropCustomer, insertCustomer),
                         "partsupp": new Table(createPartSupp, dropPartSupp, insertPartSupp),
                         "orders"  : new Table(createOrders, dropOrders, insertOrders),
                         "lineitem": new Table(createLineItem, dropLineItem, insertLineItem)]

    static String walOn = """PRAGMA journal_mode=WAL"""
    static String walOff = """PRAGMA journal_mode=DELETE"""

    static String jmOff = "PRAGMA journal_mode = OFF"
    static String synch = "PRAGMA synchronous = 0"
    static String cache = "PRAGMA cache_size = 1000000"
    // 16k pages
    static String page = "PRAGMA page_size = 16384"
    static String lock = "PRAGMA locking_mode = EXCLUSIVE"
    static String temp = "PRAGMA temp_store = MEMORY"

    static var queries = [:]

    // load queries from /queries
    static {
        new File(Stats.QUERIES_PATH).eachFile { File f ->
            if (f.name.endsWith(".sql")) {
                queries[f.name - ".sql"] = f.text
            }
        }
        println("Loaded ${queries.size()} queries")

    }
}
