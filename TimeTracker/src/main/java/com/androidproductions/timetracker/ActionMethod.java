package com.androidproductions.timetracker;

public enum ActionMethod {
    Configure(0),
    ClockIn(1<<1),
    ClockOut(2<<1),
    SwitchProject(3<<1),
    ToggleLunch(4<<1),
    ViewDay(5<<1),
    PushData(6<<1);

    public final int Value;

    ActionMethod(final int val) {
        this.Value = val;
    }

    public static ActionMethod parse(final int val)
    {
        switch (val)
        {
            case 0:
                return  ActionMethod.Configure;
            case 1<<1:
                return  ActionMethod.ClockIn;
            case 2<<1:
                return  ActionMethod.ClockOut;
            case 3<<1:
                return  ActionMethod.SwitchProject;
            case 4<<1:
                return  ActionMethod.ToggleLunch;
            case 5<<1:
                return  ActionMethod.ViewDay;
            case 6<<1:
                return  ActionMethod.PushData;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return super.toString().replaceAll("(.)([A-Z])", "$1 $2");
    }
}
