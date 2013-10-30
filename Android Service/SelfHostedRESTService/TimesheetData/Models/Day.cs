using System;
using System.Collections.Generic;

namespace TimesheetData.Models
{
    public partial class Day
    {
        public Day()
        {
            this.ProjectWorks = new List<ProjectWork>();
        }

        public string EmployeeEmail { get; set; }
        public System.DateTime Date { get; set; }
        public System.DateTime TimeStart { get; set; }
        public System.DateTime TimeFinish { get; set; }
        public decimal LunchHours { get; set; }
        public decimal HolidayHours { get; set; }
        public decimal TrainingHours { get; set; }
        public decimal Illness { get; set; }
        public decimal Research { get; set; }
        public string Comment { get; set; }
        public virtual Employee Employee { get; set; }
        public virtual ICollection<ProjectWork> ProjectWorks { get; set; }
    }
}
