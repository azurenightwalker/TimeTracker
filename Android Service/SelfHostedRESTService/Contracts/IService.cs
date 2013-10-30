using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using System.Threading.Tasks;

namespace Contracts
{
    [ServiceContract]
    public interface IService
    {
        [OperationContract]
        [WebInvoke(Method="GET", ResponseFormat=WebMessageFormat.Json)]
        List<data> GetProjects();

        [OperationContract]
        [WebInvoke]
        string PutHours(string Hours);
    }
}
