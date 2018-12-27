package br.com.senai.fplocacoes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import br.com.senai.fplocacoes.dao.ImagemDAO;
import br.com.senai.fplocacoes.dao.VeiculoDAO;
import br.com.senai.fplocacoes.model.Imagem;
import br.com.senai.fplocacoes.model.Veiculos;

public class CadastroActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button cadastrar;
    private EditText nome;
    private EditText descricao;
    private RadioButton onibus;
    private RadioButton van;
    private VeiculoDAO dao;

    private static final int PERMISSAO_REQUEST = 1;
    public static final int GALERIA_CODE = 1;
    private ImageView minhaFoto;
    private ImageButton colocarFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cadastrar = findViewById(R.id.btnCadastro);
        nome = findViewById(R.id.editNome);
        descricao = findViewById(R.id.editDescricao);
        onibus = findViewById(R.id.radioOnibus);
        van =  findViewById(R.id.radioVan);

        minhaFoto = findViewById(R.id.imgBusVan);
        colocarFoto = findViewById(R.id.btnSelecionarFoto);


        dao = new VeiculoDAO(this);

        final VeiculoHelper help = new VeiculoHelper(CadastroActivity.this);

        Bundle extras = getIntent().getExtras();
        Long veiculoId = (extras != null) ? extras.getLong("veiculoId") : null;


        if (veiculoId == null) {
            Veiculos veiculo = new Veiculos();
        } else{
            Veiculos veiculo = dao.localizar(veiculoId);
            help.preencherVeiculo(veiculo);
        }


        colocarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =
                        new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALERIA_CODE);
            }
        });

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // SETAR A IMAGEM AQUII
                Imagem imagem = new Imagem();
                imagem.setCaminhoDaFoto(minhaFoto.getTag().toString());
                ImagemDAO imgDao = new ImagemDAO(CadastroActivity.this);
                imgDao.inserir(imagem);

                if(nome.getText().length() == 0 && descricao.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Todos os campos são obrigatórios!", Toast.LENGTH_LONG).show();
                } else if (nome.getText().length() != 0 && descricao.getText().length() != 0) {

                    if (onibus.isChecked() && van.isChecked()) {
                        Toast.makeText(getApplicationContext(), "Escolha apenas um tipo veículo!", Toast.LENGTH_LONG).show();
                    } else if (onibus.isChecked() || van.isChecked()) {



                        String bus = onibus.getText().toString();
                        //BUNDLE

                        Veiculos veiculo1 = help.pegarVeiculo();
                        VeiculoDAO dao = new VeiculoDAO(CadastroActivity.this);

                        //salvar

                        if (veiculo1.getId() != null) {
                            dao.editar(veiculo1);
                            Toast.makeText(getApplicationContext(), "Seu veiculo foi editado", Toast.LENGTH_SHORT).show();
                        } else {
                            dao.salvar(veiculo1);
                            Toast.makeText(getApplicationContext(), "Seu veiculo foi enviado com sucesso", Toast.LENGTH_SHORT).show();
                        }

                        Intent intent = new Intent(CadastroActivity.this, ListaActivity.class);
                        startActivity(intent);

                        dao.close();
                        finish();

                    }
                }
            }


        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cadastro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == GALERIA_CODE){
            Uri imagemSelecionada = data.getData();
            String[] caminhoDoDiretorio = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(imagemSelecionada,caminhoDoDiretorio,null,null,null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(caminhoDoDiretorio[0]);
            String caminhoDaImagem = c.getString(columnIndex);
            c.close();
            Bitmap imagemRetornada = (BitmapFactory.decodeFile(caminhoDaImagem));
            minhaFoto.setImageBitmap(imagemRetornada);
            minhaFoto.setTag(caminhoDaImagem);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == PERMISSAO_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // A permissão foi concedida. Pode continuar
            } else{
                // A permissão foi negada. Precisa ver o que deve ser desabilitado

            }
            return;
        }
    }


}
