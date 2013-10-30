using System;
using System.Collections.Generic;

namespace TimesheetData.Models
{
    public partial class ProjectWork
    {
        public string EmployeeEmail { get; set; }
        public System.DateTime Date { get; set; }
        public string Project { get; set; }
        public decimal Dev { get; set; }
        public decimal Support { get; set; }
        public decimal Research { get; set; }
        public virtual Day Day { get; set; }
        public virtual Employee Employee { get; set; }
        public virtual Project Project1 { get; set; }
    }
}
