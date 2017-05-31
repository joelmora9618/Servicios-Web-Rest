using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Web;
using WebApplication3.Models;

namespace WebApplication3.Models
{
    public class AlumnoManager
    {
       // String cadenaConexion = @"Data Source=WKS831L;Initial Catalog=practica7;Integrated Security=True";
        String cadenaConexion = @"Data Source=JemDBase.mssql.somee.com;Initial Catalog=JemDBase;Persist Security Info=True;User ID=WKS831L_SQLLogin_1;Password=m1kz4gyxuw;MultipleActiveResultSets=True;Application Name=EntityFramework";


        public bool InsertarAlumno(alumno alu)
        {
            SqlConnection con = new SqlConnection(cadenaConexion);
            con.Open();
            string sql = "INSERT INTO alumno (dni_alumno, nombre, apellido, edad, sexo, divicion) VALUES (@dni_alumno, @nombre, @apellido, @edad, @sexo, @divicion)";
            SqlCommand cmd = new SqlCommand(sql, con);
            cmd.Parameters.Add("@dni_alumno", System.Data.SqlDbType.Int).Value = alu.dni_alumno;
            cmd.Parameters.Add("@nombre", System.Data.SqlDbType.NVarChar).Value = alu.nombre;
            cmd.Parameters.Add("@apellido", System.Data.SqlDbType.NVarChar).Value = alu.apellido;
            cmd.Parameters.Add("@edad", System.Data.SqlDbType.Int).Value = alu.edad;
            cmd.Parameters.Add("@sexo", System.Data.SqlDbType.NVarChar).Value = alu.sexo;
            cmd.Parameters.Add("@divicion", System.Data.SqlDbType.NVarChar).Value = alu.divicion;
            int res = cmd.ExecuteNonQuery();
            con.Close();
            return (res == 1);
        }

        public bool ActualizarAlumno(alumno alu)
        {
            SqlConnection con = new SqlConnection(cadenaConexion);
            con.Open();
            string sql = "UPDATE alumno SET nombre = @nombre, apellido = @apellido, edad = @edad, sexo = @sexo, divicion = @divicion WHERE dni_alumno = @dni_alumno";
            SqlCommand cmd = new SqlCommand(sql, con);
            cmd.Parameters.Add("@dni_alumno", System.Data.SqlDbType.Int).Value = alu.dni_alumno;
            cmd.Parameters.Add("@nombre", System.Data.SqlDbType.NVarChar).Value = alu.nombre;
            cmd.Parameters.Add("@apellido", System.Data.SqlDbType.NVarChar).Value = alu.apellido;
            cmd.Parameters.Add("@edad", System.Data.SqlDbType.Int).Value = alu.edad;
            cmd.Parameters.Add("@sexo", System.Data.SqlDbType.NVarChar).Value = alu.sexo;
            cmd.Parameters.Add("@divicion", System.Data.SqlDbType.NVarChar).Value = alu.divicion;

            int res = cmd.ExecuteNonQuery();
            con.Close();
            return (res == 1);
        }
        public alumno ObtenerAlumno(int dni)
        {
            alumno alu = null;
            SqlConnection con = new SqlConnection(cadenaConexion);
            con.Open();
            string sql = "SELECT dni_alumno, nombre, apellido, edad, sexo, divicion FROM alumno WHERE dni_alumno = @dni_alumno";
            SqlCommand cmd = new SqlCommand(sql, con);
            cmd.Parameters.Add("@dni_alumno", System.Data.SqlDbType.NVarChar).Value = dni;  //probar tambien con INT
            SqlDataReader reader =
                cmd.ExecuteReader(System.Data.CommandBehavior.CloseConnection);
            if (reader.Read())
            {
                alu = new alumno();
                alu.dni_alumno = dni;
                alu.dni_alumno = reader.GetInt32(0);
                alu.nombre = reader.GetString(1);
                alu.apellido = reader.GetString(2);
                alu.edad = reader.GetInt32(3);
                alu.sexo = reader.GetString(4);
                alu.divicion = reader.GetString(5);
            }
            reader.Close();
            return alu;
        }

        public List<alumno> ObtenerAlumno()
        {
            List<alumno> lista = new List<alumno>();
            SqlConnection con = new SqlConnection(cadenaConexion);
            con.Open();
            string sql = "SELECT dni_alumno, nombre, apellido, edad, sexo, divicion FROM alumno";
            SqlCommand cmd = new SqlCommand(sql, con);
            SqlDataReader reader =
                cmd.ExecuteReader(System.Data.CommandBehavior.CloseConnection);
            while (reader.Read())
            {
                alumno alu = new alumno();
                alu = new alumno();
                alu.dni_alumno = reader.GetInt32(0);
                alu.nombre = reader.GetString(1);
                alu.apellido = reader.GetString(2);
                alu.edad = reader.GetInt32(3);
                alu.sexo = reader.GetString(4);
                alu.divicion = reader.GetString(5);
                lista.Add(alu);
            }
            reader.Close();
            return lista;
        }

        public bool EliminarAlumno(int dni)
        {
            SqlConnection con = new SqlConnection(cadenaConexion);
            con.Open();
            string sql = "DELETE FROM alumno WHERE dni_alumno = @dni_alumno";
            SqlCommand cmd = new SqlCommand(sql, con);
            cmd.Parameters.Add("@dni_alumno", System.Data.SqlDbType.Int).Value = dni;
            int res = cmd.ExecuteNonQuery();
            con.Close();
            return (res == 1);
        }
    }
}