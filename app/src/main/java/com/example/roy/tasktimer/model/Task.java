package com.example.roy.tasktimer.model;

import java.io.Serializable;

/**
 * Created by Roy on 7/11/17.
 */

public class Task implements Serializable {
    public static final long serialVersionUID = 20161120L;
    private final String mName;
    private final String mDescription;
    private final int mSortOrder;
    private long m_id;

    public Task(long id, String mName, String mDescription, int mSortOrder) {
        this.m_id = id;
        this.mName = mName;
        this.mDescription = mDescription;
        this.mSortOrder = mSortOrder;
    }

    public long getId() {
        return m_id;
    }

    public void setId(long id) {
        this.m_id = m_id;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getSortOrder() {
        return mSortOrder;
    }

    @Override
    public String toString() {
        return "Task{" +
                "m_id=" + m_id +
                ", mName='" + mName + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mSortOrder=" + mSortOrder +
                '}';
    }
}
