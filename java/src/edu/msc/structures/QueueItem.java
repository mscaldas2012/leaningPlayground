package edu.msc.structures;

/**
 * Created by marcelo on 3/25/16.
 */
 class QueueItem {
    private Object item;
    private QueueItem next;

    public QueueItem(Object item) {
        this.item = item;
    }

    public QueueItem getNext() {
        return next;
    }

    public void setNext(QueueItem next) {
        this.next = next;
    }

    public Object getItem() {

        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }
}