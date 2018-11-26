package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ToDoItem implements Serializable {

    private String date;
    private String content;
    private boolean done;

    public ToDoItem(String content)
    {
        this.content = content;
        this.date = new SimpleDateFormat("d.M.yyyy").format(new Date());    //nastaveni formatu dat
        this.done = false;

    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done)   //setter na done
    {
        this.done = done;
    }

    public void setContent(String aValue) {
        this.content = aValue;
    }
}
