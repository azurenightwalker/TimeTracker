package com.androidproductions.timetracker;

public enum ActionMethod {
    ClockIn(0),
    ClockOut(1<<1),
    SwitchProject(2<<1),
    ViewDay(3<<1);

    public final int Value;

    ActionMethod(final int val) {
        this.Value = val;
    }

    public static ActionMethod parse(final int val)
    {
        switch (val)
        {
            case 0:
                return  ActionMethod.ClockIn;
            case 1<<1:
                return  ActionMethod.ClockOut;
            case 2<<1:
                return  ActionMethod.SwitchProject;
            case 3<<1:
                return  ActionMethod.ViewDay;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return super.toString().replaceAll("(.)([A-Z])", "$1 $2");
    }
}
