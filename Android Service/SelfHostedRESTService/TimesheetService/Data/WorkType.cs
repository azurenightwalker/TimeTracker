namespace TimesheetService.Data
{
    public enum WorkType
    {
        Dev = 0,
        Support = 1 << 1,
        Sales = 2 << 1,
        Research = 3 << 1
    }
}