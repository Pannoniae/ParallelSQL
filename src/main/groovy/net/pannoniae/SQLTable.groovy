package net.pannoniae

class Table {
    String create
    String drop
    String insert

    public Table(String create, String drop, String insert) {
        this.create = create
        this.drop = drop
        this.insert = insert
    }
}
