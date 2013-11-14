namespace TimesheetService.Data
{
    public class ProjectDescription
    {
        public string Name { get; set; }
        public bool HasDev { get; set; }
        public bool HasSupport { get; set; }
        public bool HasResearch { get; set; }
        public bool HasSales { get; set; }
    }
}
