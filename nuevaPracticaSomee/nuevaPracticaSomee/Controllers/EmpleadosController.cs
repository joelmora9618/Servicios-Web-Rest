using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using nuevaPracticaSomee.Models;

namespace nuevaPracticaSomee.Controllers
{
    public class EmpleadosController : ApiController
    {
        EmpleadoManager empleadoManager = new EmpleadoManager();
        public IEnumerable<empleado> Get()
        {
            var listaEmpleados = empleadoManager.ObtenerEmpleado();
            return listaEmpleados;
        }

        [HttpGet]
        public empleado Get(int dni)
        {
            return empleadoManager.ObtenerEmpleado(dni);

        }

        [HttpPost]
        public void Post(empleado item)
        {
            empleadoManager.InsertarEmpleado(item);
        }

    }
}
