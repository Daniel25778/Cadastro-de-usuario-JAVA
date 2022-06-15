package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

    public class SQLHelper extends SQLiteOpenHelper {

        /**  ATRIBUTOS DA CLASSE DE CONNECTION  **/

        private static final  String DB_NAME = "softwareHouse";
        private static final int DB_VERSION = 3;
        private static database.SQLHelper INSTANCE ;


        /* Método de verificar se a conexão esta aberta  */

        public static database.SQLHelper getInstance(Context context){

            if (INSTANCE == null){

                INSTANCE = new database.SQLHelper(context);

            }

            return  INSTANCE;
        }

        /* Método construtor: Recebe os valores iniciais de abertura da conexão  */

        public SQLHelper(@Nullable Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            sqLiteDatabase.execSQL("CREATE TABLE tbl_usuario" +
                    "(cod_usuario INTEGER PRIMARY KEY," +
                    "nome TEXT," +
                    "sobrenome TEXT," +
                    "login TEXT," +
                    "senha TEXT," +
                    "created_date DATETIME)");

            Log.d("SQLITE-", "BANCO DE DADOS CRIADO! ˜" + DB_VERSION);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


            sqLiteDatabase.execSQL("CREATE TABLE tbl_endereco" +
                    "(cod_endereco INTEGER PRIMARY KEY," +
                    "cod_usuario INTEGER," +
                    "cep TEXT," +
                    "complemento TEXT," +
                    "numero TEXT,"+
                    "FOREIGN KEY (cod_usuario) REFERENCES tbl_usuario(cod_usuario))");

            Log.d("SQLITE-", "BANCO DE DADOS CRIADO! ˜" + DB_VERSION);

        }

        public int addUser(String nome, String sobrenome, String login, String senha, String created_date){

            //Configura o SQLITE para escrita:

            SQLiteDatabase sqLiteDatabase = getWritableDatabase();

            try{
                sqLiteDatabase.beginTransaction();

                ContentValues values = new ContentValues();

                values.put("nome", nome);
                values.put("sobrenome", sobrenome);
                values.put("login", login);
                values.put("senha", senha);
                values.put("created_date", created_date);

               int cod_usuario = (int) sqLiteDatabase.insertOrThrow("tbl_usuario", null, values);
                sqLiteDatabase.setTransactionSuccessful();

                return  cod_usuario;
            }

            catch (Exception e){
                Log.d("SQLITE˜", e.getMessage());
                return  0;
            }

            finally {

                if (sqLiteDatabase.isOpen()){
                    sqLiteDatabase.endTransaction();
                }

            }


        }

        public boolean addEndereco(int cod_usuario, String cep, String complemento, String numero){

            //Configura o SQLITE para escrita:

            SQLiteDatabase sqLiteDatabase = getWritableDatabase();

            try{
                sqLiteDatabase.beginTransaction();

                ContentValues values = new ContentValues();


                values.put("cod_usuario", cod_usuario);
                values.put("cep", cep);
                values.put("complemento", complemento);
                values.put("numero", numero);

                sqLiteDatabase.insertOrThrow("tbl_endereco", null, values);
                sqLiteDatabase.setTransactionSuccessful();

                return  true;
            }

            catch (Exception e){
                Log.d("SQLITE˜", e.getMessage());
                return  false;
            }

            finally {

                if (sqLiteDatabase.isOpen()){
                    sqLiteDatabase.endTransaction();
                }

            }


        }


        //REALIZAR LOGIN

        public int login(String login, String senha){

            SQLiteDatabase sqLiteDatabase = getReadableDatabase();

            Cursor cursor = sqLiteDatabase.rawQuery(
                    "SELECT * FROM tbl_usuario WHERE login = ? AND senha = ?",
                    new String[]{login,senha}
            );
            int cod_usuario = 0;
            try{

                if (cursor.moveToFirst()){

                    cod_usuario = cursor.getInt(cursor.getColumnIndex("cod_usuario"));
                    return cod_usuario;
                }

                return 0;

            }catch (Exception e){

                Log.d("SQLITE˜", e.getMessage());

            }finally{

                if (cursor != null && !cursor.isClosed()){

                    cursor.close();
                }

            }
            return 0;

        }

}
