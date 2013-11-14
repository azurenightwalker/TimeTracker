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
                TimeIn = day.TimeStart,
                TimeOut = day.TimeFinish,
                Work = GetWorksList(day),
                Holiday = day.HolidayHours,
                Lunch = day.LunchHours,
                Training = day.TrainingHours
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
