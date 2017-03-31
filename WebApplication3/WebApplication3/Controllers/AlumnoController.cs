using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using WebApplication3.Models;

namespace WebApplication3.Controllers
{
    public class AlumnoController : Controller
    {
        JEMPracticas_dbEntities entidad = new JEMPracticas_dbEntities();
        AlumnoManager alumnoManager;

        public AlumnoController()
        {
            alumnoManager = new AlumnoManager();
        }

        // GET: Alumno
        [HttpGet]
        public JsonResult Alumnos()
        {
            //var lista = entidad.alumno.ToList();
            // return View(lista.ToList());
            return this.Json(alumnoManager.ObtenerAlumno(), JsonRequestBehavior.AllowGet);
        }

        public JsonResult Alumno(int? dni, alumno item)
        {
            switch (Request.HttpMethod)
            {
                case "POST":
                    return Json(alumnoManager.InsertarAlumno(item));
                case "PUT":
                    return Json(alumnoManager.ActualizarAlumno(item));
                case "GET":
                    return Json(alumnoManager.ObtenerAlumno(dni.GetValueOrDefault()),
                                JsonRequestBehavior.AllowGet);
                case "DELETE":
                    return Json(alumnoManager.EliminarAlumno(dni.GetValueOrDefault()));
            }

            return Json(new { Error = true, Message = "Operación HTTP desconocida" });
        }
    }
}