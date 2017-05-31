using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Web;

namespace nuevaPracticaSomee.Models
{
    public class EmpleadoManager
    {
        String cadenaConexion = @"Data Source=Jem96Db.mssql.somee.com;Initial Catalog=Jem96Db;Persist Security Info=True;User ID=WKS831L_SQLLogin_1;Password=m1kz4gyxuw;MultipleActiveResultSets=True;Application Name=EntityFramework";

        public List<empleado> ObtenerEmpleado()
        {
            List<empleado> lista = new List<empleado>();
            SqlConnection con = new SqlConnection(cadenaConexion);
            con.Open();
            string sql = "SELECT dni_empleado, nombre, apellido, fecha_de_nacimiento, sexo, password FROM empleado";
            SqlCommand cmd = new SqlCommand(sql, con);
            SqlDataReader reader =
                cmd.ExecuteReader(System.Data.CommandBehavior.CloseConnection);
            while (reader.Read())
            {
                empleado emp = new empleado();
                emp = new empleado();
                emp.dni_empleado = reader.GetInt32(0);
                emp.nombre = reader.GetString(1);
                emp.apellido = reader.GetString(2);
                emp.fecha_de_nacimiento = reader.GetDateTime(3);
                emp.sexo = reader.GetString(4);
                emp.password = reader.GetString(5);
                lista.Add(emp);
            }
            reader.Close();
            return lista;
        }

        public empleado ObtenerEmpleado(int dni)
        {
            empleado emp = null;
            SqlConnection con = new SqlConnection(cadenaConexion);
            con.Open();
            string sql = "SELECT dni_empleado, nombre, apellido, fecha_de_nacimiento, sexo, password FROM empleado WHERE dni_empleado = @dni_empleado";
            SqlCommand cmd = new SqlCommand(sql, con);
            cmd.Parameters.Add("@dni_empleado", System.Data.SqlDbType.NVarChar).Value = dni;
            SqlDataReader reader =
                cmd.ExecuteReader(System.Data.CommandBehavior.CloseConnection);
            if (reader.Read())
            {
                emp = new empleado();
                emp.dni_empleado = dni;
                emp.dni_empleado = reader.GetInt32(0);
                emp.nombre = reader.GetString(1);
                emp.apellido = reader.GetString(2);
                emp.fecha_de_nacimiento = reader.GetDateTime(3);
                emp.sexo = reader.GetString(4);
                emp.password = reader.GetString(5);
            }
            reader.Close();
            return emp;
        }

        public bool InsertarEmpleado(empleado emp)
        {
            SqlConnection con = new SqlConnection(cadenaConexion);
            con.Open();
            string sql = "INSERT INTO empleado (dni_empleado, nombre, apellido, fecha_de_nacimiento, sexo, password) VALUES (@dni_empleado, @nombre, @apellido, @fecha_de_nacimiento, @sexo, @password)";
            SqlCommand cmd = new SqlCommand(sql, con);
            cmd.Parameters.Add("@dni_empleado", System.Data.SqlDbType.Int).Value = emp.dni_empleado;
            cmd.Parameters.Add("@nombre", System.Data.SqlDbType.NVarChar).Value = emp.nombre;
            cmd.Parameters.Add("@apellido", System.Data.SqlDbType.NVarChar).Value = emp.apellido;
            cmd.Parameters.Add("@fecha_de_nacimiento", System.Data.SqlDbType.DateTime).Value = emp.fecha_de_nacimiento;
            cmd.Parameters.Add("@sexo", System.Data.SqlDbType.NVarChar).Value = emp.sexo;
            cmd.Parameters.Add("@password", System.Data.SqlDbType.NVarChar).Value = emp.password;

            int res = cmd.ExecuteNonQuery();
            con.Close();
            return (res == 1);
        }
    }
}