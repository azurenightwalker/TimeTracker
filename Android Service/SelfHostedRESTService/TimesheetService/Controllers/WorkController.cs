using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Http;
using TimesheetData.Models;
using TimesheetService.Data;
using WebGrease.Css.Extensions;

namespace TimesheetService.Controllers
{
    public class WorkController : ApiController
    {
        // GET api/work/scott.jones@x-labsystems.co.uk
        public IEnumerable<EmployeeWork> Get(string id)
        {
            using (TimesheetsContext tdb = new TimesheetsContext())
            {
                var emp = tdb.Employees.SingleOrDefault(a => a.Email == id);
                if (emp == null)
                    return null;
                var res = new List<EmployeeWork>();
                emp.Days.OrderByDescending(a => a.Date).ForEach(day => res.Add(GenerateEmployeeWork(day)));
                return res;
            }
        }

        // GET api/work/scott.jones@x-labsystems.co.uk/2013-11-13
        public EmployeeWork Get(string employee, DateTime date)
        {
            using (TimesheetsContext tdb = new TimesheetsContext())
            {
                var day = tdb.Days.SingleOrDefault(a => a.EmployeeEmail == employee && a.Date == date);
                if (day == null)
                    return null;
                return GenerateEmployeeWork(day);
            }
        }

        private static EmployeeWork GenerateEmployeeWork(Day day)
        {
            return new EmployeeWork
            {
                Date = day.Date,
                TimeIn = day.TimeStart,
                TimeOut = day.TimeFinish,
                Work = GetWorksList(day).ToDictionary(a => a.Project + " - " + a.WorkType.ToString(), b => b.Hours),
                Holiday = day.HolidayHours,
                Lunch = day.LunchHours,
                Training = day.TrainingHours,
                Illness = day.Illness,
                Research = day.Research
            };
        }

        private static IEnumerable<Work> GetWorksList(Day day)
        {
            var pws = new List<Work>();
            foreach (var projectWork in day.ProjectWorks)
            {
                if (projectWork.Dev > 0)
                    pws.Add(new Work
                    {
                        Hours = projectWork.Dev,
                        Project = projectWork.Project,
                        WorkType = WorkType.Dev
                    });
                if (projectWork.Support > 0)
                    pws.Add(new Work
                    {
                        Hours = projectWork.Support,
                        Project = projectWork.Project,
                        WorkType = WorkType.Support
                    });
                if (projectWork.Sales > 0)
                    pws.Add(new Work
                    {
                        Hours = projectWork.Sales,
                        Project = projectWork.Project,
                        WorkType = WorkType.Sales
                    });
                if (projectWork.Research > 0)
                    pws.Add(new Work
                    {
                        Hours = projectWork.Research,
                        Project = projectWork.Project,
                        WorkType = WorkType.Research
                    });
            }
            return pws;
        }

        // POST api/work/scott.jones@x-labsystems.co.uk
        public void Post(string employee, [FromBody]EmployeeWork data)
        {
            employee = employee.Contains("@") ? employee : employee + "@x-labsystems.co.uk";
            using (TimesheetsContext tdb = new TimesheetsContext())
            {
                var day = new Day
                {
                    Date = data.Date,
                    EmployeeEmail = employee,
                    HolidayHours = data.Holiday,
                    Illness = data.Illness,
                    LunchHours = data.Lunch,
                    Research = data.Research,
                    TimeFinish = data.TimeOut,
                    TimeStart = data.TimeIn,
                    TrainingHours = data.Training
                };
                tdb.Days.Add(day);
                foreach (var wk in data.Work)
                {
                    var parts = wk.Key.Split('-').Select(a => a.Trim()).ToList();
                    var pw = day.ProjectWorks.SingleOrDefault(a => a.Project == parts[0]);
                    if (pw == null)
                    {
                        pw = new ProjectWork
                        {
                            Date = data.Date,
                            EmployeeEmail = employee
                        };
                        day.ProjectWorks.Add(pw);
                    }
                    switch (parts[1])
                    {
                        case "Dev":
                            pw.Dev += wk.Value;
                            break;
                        case "Support":
                            pw.Support += wk.Value;
                            break;
                        case "Sales":
                            pw.Sales += wk.Value;
                            break;
                        case "Research":
                            pw.Research += wk.Value;
                            break;
                    }
                }
                tdb.SaveChanges();
            }
        }

        // PUT api/work/scott.jones@x-labsystems.co.uk/2013-11-13
        public void Put(string employee, DateTime date, [FromBody]EmployeeWork data)
        {
        }

        // DELETE api/work/scott.jones@x-labsystems.co.uk/2013-11-13
        public void Delete(string employee, DateTime date)
        {
        }
    }
}
