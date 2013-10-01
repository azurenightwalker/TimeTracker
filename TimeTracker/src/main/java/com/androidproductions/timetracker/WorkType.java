package com.androidproductions.timetracker;

public enum WorkType {
    Dev(0),
    Support(1<<1),
    Research(2<<1),
    Training(3<<1);

    public final int Value;

    WorkType(final int val) {
        this.Value = val;
    }

    public static WorkType parse(final int val)
    {
        switch (val)
        {
            case 0:
                return  WorkType.Dev;
            case 1<<1:
                return  WorkType.Support;
            case 2<<1:
                return  WorkType.Research;
            case 3<<1:
                return  WorkType.Training;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return super.toString().replaceAll("(.)([A-Z])", "$1 $2");
    }
}
