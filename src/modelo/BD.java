package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BD {

    private static Connection con = null;

    private static final String DB_URL = "jdbc:mysql://ingreq2021-mysql.cobadwnzalab.eu-central-1.rds.amazonaws.com";
    private static final String DB_SCHEMA = "apsgrupo10";
    private static final String USER = "grupo10";
    private static final String PASS = "guillermoeduardo2021";
    public BD(){
        try {
            if (con == null) {
                con = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA,USER,PASS);
            }
        } catch(SQLException ex) {
            throw new ErrorBD("Error al Conectar con la base de datos." + ex.getMessage());
        }
    }

    public static void cerrarConexiones() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                throw new ErrorBD("Error al cerrar la conexión: " + ex.getMessage());
            }
        }
    }

    public Object SelectEscalar(String sel)
    {
        ResultSet rset;
        Object res = null;
        try
        {
            Statement stmt = con.createStatement();
            rset = stmt.executeQuery(sel);
            rset.next();
            res = rset.getObject(1);
            rset.close();
            stmt.close();
        }
        catch (SQLException ex)
        {
            throw new ErrorBD("Error en el SELECT: " + sel + ". " + ex.getMessage());
        }

        return res;
    }

    public List<Object[]> Select(String sel)
    {
        ResultSet rset;
        List<Object[]> lista = new ArrayList<Object[]>();
        try
        {
            Statement stmt = con.createStatement();
            rset = stmt.executeQuery(sel);
            ResultSetMetaData meta = rset.getMetaData();
            int numCol = meta.getColumnCount();
            while (rset.next())
            {
                Object[] tupla = new Object[numCol];
                for(int i=0; i<numCol;++i)
                {
                    tupla[i] = rset.getObject(i+1);
                }
                lista.add(tupla);
            }
            rset.close();
            stmt.close();
        }
        catch (SQLException ex)
        {
            throw new ErrorBD("Error en el SELECT: " + sel+ ". " + ex.getMessage());
        }

        return lista;
    }

    public void Insert(String ins)
    {
        try
        {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(ins);
            stmt.close();
        }
        catch (SQLException ex)
        {
            throw new ErrorBD("Error en el INSERT: " + ins+ ". " + ex.getMessage());
        }
    }

    public void Delete(String del)
    {
        try
        {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(del);
            stmt.close();
        }
        catch (SQLException ex)
        {
            throw new ErrorBD("Error en el DELETE: " + del+ ". " + ex.getMessage());
        }
    }

    public void Update(String up)
    {
        try
        {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(up);
            stmt.close();
        }
        catch (SQLException ex)
        {
            throw new ErrorBD("Error en el UPDATE: " + up+ ". " + ex.getMessage());
        }
    }

    public void CallProcedure(String name , Object[] param)
    {
        try
        {
            if (param==null || param.length == 0)
            {
                Statement stmt = con.createStatement();
                stmt.executeQuery("{call  " + name + "}");
                stmt.close();
            }
            else
            {
                String sentencia = 	"{call " + name + " (?";
                for(int i=1;i<param.length;++i) sentencia+=",?";
                sentencia+= ")}";
                PreparedStatement pstmt = con.prepareStatement(sentencia);
                for(int i=0;i<param.length;++i) pstmt.setObject(i+1, param[i]);
                pstmt.execute();
                pstmt.close();
            }
        }
        catch (SQLException ex)
        {
            throw new ErrorBD("Error en el CallProcedure: " + name+ ". " + ex.getMessage());
        }
    }

    public Object FunctionCall(String name , int retType, Object[] param)
    {
        Object res = null;
        String sentencia = 	"{? = call " + name +" (";
        int l = (param==null?0:param.length);

        try
        {
            if ( l  > 0)
            {
                sentencia+= "?";
                for(int i=1;i<param.length;++i) sentencia+=",?";
            }
            sentencia+= ")}";

            CallableStatement  cstmt = con.prepareCall(sentencia);
            cstmt.registerOutParameter(1,retType);

            for(int i=0;i<param.length;++i) cstmt.setObject(i+2, param[i]);
            cstmt.execute();
            res = cstmt.getObject(1);
            cstmt.close();

        }
        catch (SQLException ex)
        {
            throw new ErrorBD("Error en el FunctionCall: " + name+ ". " + ex.getMessage());
        }

        return res;
    }

    public String[] getNombreColumnas(String tableName)
    {
        String[] res = null;

        try
        {
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery("SELECT * FROM " + tableName + ";");
            ResultSetMetaData rsetMD = rset.getMetaData();
            res = new String[rsetMD.getColumnCount()];
            for(int i=0;i<rsetMD.getColumnCount();++i) res[i] = rsetMD.getColumnName(i+1);
        }
        catch (SQLException ex)
        {
            throw new ErrorBD("Error en el getNombreColumnas: " + tableName+ ". " + ex.getMessage());
        }

        return res;
    }
}
