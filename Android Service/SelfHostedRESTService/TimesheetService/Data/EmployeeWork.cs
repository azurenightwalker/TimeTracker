using System;
using System.Collections;
using System.Collections.Generic;
using TimesheetData.Models;

namespace TimesheetService.Data
{
    public class EmployeeWork
    {
        public DateTime TimeIn { get; set; }
        public DateTime TimeOut { get; set; }
        public IEnumerable<Work> Work { get; set; }
        public decimal Holiday { get; set; }
        public decimal Lunch { get; set; }
        public decimal Training { get; set; }
    }
}
