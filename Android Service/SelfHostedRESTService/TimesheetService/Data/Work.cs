namespace TimesheetService.Data
{
    public class Work
    {
        public string Project { get; set; }
        public WorkType WorkType { get; set; }
        public decimal Hours { get; set; }
    }
}
