package com.betterino.magnus.wonderbetterino_mm.Games.Galgeleg;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.betterino.magnus.wonderbetterino_mm.LobbyDTO;
import com.betterino.magnus.wonderbetterino_mm.R;

public class Hangman extends AppCompatActivity implements View.OnClickListener {

    private Galgelogik spillet = new Galgelogik();
    private ImageView galgePic;
    private TextView ordet;
    private Button ba, bb, bc, bd, be, bf, bg,
            bh, bi, bj, bk, bl, bm, bn,
            bo, bp, bq, br, bs, bt, bu,
            bv, bw, bx, by, bz, bæ, bø, bå;

    private int forkerteGæt;
    private LobbyDTO lobby;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);


        lobby = (LobbyDTO) getIntent().getSerializableExtra("lobby");
        userID = (String) getIntent().getStringExtra("userID");



        galgePic = (ImageView) findViewById(R.id.galgePic1);
        ordet = (TextView) findViewById(R.id.galgeText1);
        makeButtons();
        makeHandlers();
        //hentOrdVedStart(); //Virker ikke eller er meget langsom.
        vedStart();


    }

    public void vedStart() {
        spillet.nulstil();
        ordet.setText(spillet.getSynligtOrd());
        forkerteGæt = spillet.getAntalForkerteBogstaver();
    }

    public void hentOrdVedStart() {
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object... arg0) {
                try {
                    spillet.hentOrdFraDr();
                    return "Ordene blev hentet \n";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Ordene blev ikke hentet \n" + e;
                }
            }
            @Override
            protected void onPostExecute(Object resultat) {
                vedStart();
            }
        }.execute();
    }

    public void vedSlut() {
        Intent i = new Intent(this, GameOverActivity.class);
        i.putExtra("lobby", lobby);
        i.putExtra("userID", userID);
        int a = 7-spillet.getAntalForkerteBogstaver();
        String b = ""+a;
        i.putExtra("score", b);
        startActivity(i);

        finish();





    }








    public void makeButtons() {
        ba = (Button) findViewById(R.id.buttonBA);
        bb = (Button) findViewById(R.id.buttonBB);
        bc = (Button) findViewById(R.id.buttonBC);
        bd = (Button) findViewById(R.id.buttonBD);
        be = (Button) findViewById(R.id.buttonBE);
        bf = (Button) findViewById(R.id.buttonBF);
        bg = (Button) findViewById(R.id.buttonBG);
        bh = (Button) findViewById(R.id.buttonBH);
        bi = (Button) findViewById(R.id.buttonBI);
        bj = (Button) findViewById(R.id.buttonBJ);
        bk = (Button) findViewById(R.id.buttonBK);
        bl = (Button) findViewById(R.id.buttonBL);
        bm = (Button) findViewById(R.id.buttonBM);
        bn = (Button) findViewById(R.id.buttonBN);
        bo = (Button) findViewById(R.id.buttonBO);
        bp = (Button) findViewById(R.id.buttonBP);
        bq = (Button) findViewById(R.id.buttonBQ);
        br = (Button) findViewById(R.id.buttonBR);
        bs = (Button) findViewById(R.id.buttonBS);
        bt = (Button) findViewById(R.id.buttonBT);
        bu = (Button) findViewById(R.id.buttonBU);
        bv = (Button) findViewById(R.id.buttonBV);
        bw = (Button) findViewById(R.id.buttonBW);
        bx = (Button) findViewById(R.id.buttonBX);
        by = (Button) findViewById(R.id.buttonBY);
        bz = (Button) findViewById(R.id.buttonBZ);
        bæ = (Button) findViewById(R.id.buttonBÆ);
        bø = (Button) findViewById(R.id.buttonBØ);
        bå = (Button) findViewById(R.id.buttonBÅ);
    }


    public void makeHandlers(){
        ba.setOnClickListener(this);
        bb.setOnClickListener(this);
        bc.setOnClickListener(this);
        bd.setOnClickListener(this);
        be.setOnClickListener(this);
        bf.setOnClickListener(this);
        bg.setOnClickListener(this);
        bh.setOnClickListener(this);
        bi.setOnClickListener(this);
        bj.setOnClickListener(this);
        bk.setOnClickListener(this);
        bl.setOnClickListener(this);
        bm.setOnClickListener(this);
        bn.setOnClickListener(this);
        bo.setOnClickListener(this);
        bp.setOnClickListener(this);
        bq.setOnClickListener(this);
        br.setOnClickListener(this);
        bs.setOnClickListener(this);
        bt.setOnClickListener(this);
        bu.setOnClickListener(this);
        bv.setOnClickListener(this);
        bw.setOnClickListener(this);
        bx.setOnClickListener(this);
        by.setOnClickListener(this);
        bz.setOnClickListener(this);
        bæ.setOnClickListener(this);
        bø.setOnClickListener(this);
        bå.setOnClickListener(this);
    }



    public void onClick(View v) {

        if (ba.isPressed()) {
            spillet.gætBogstav("a");
            ba.setVisibility(View.INVISIBLE);
        } else if (bb.isPressed()) {
            spillet.gætBogstav("b");
            bb.setVisibility(View.INVISIBLE);
        } else if (bc.isPressed()) {
            spillet.gætBogstav("c");
            bc.setVisibility(View.INVISIBLE);
        } else if (bd.isPressed()) {
            spillet.gætBogstav("d");
            bd.setVisibility(View.INVISIBLE);
        } else if (be.isPressed()) {
            spillet.gætBogstav("e");
            be.setVisibility(View.INVISIBLE);
        } else if (bf.isPressed()) {
            spillet.gætBogstav("f");
            bf.setVisibility(View.INVISIBLE);
        } else if (bg.isPressed()) {
            spillet.gætBogstav("g");
            bg.setVisibility(View.INVISIBLE);
        } else if (bh.isPressed()) {
            spillet.gætBogstav("h");
            bh.setVisibility(View.INVISIBLE);
        } else if (bi.isPressed()) {
            spillet.gætBogstav("i");
            bi.setVisibility(View.INVISIBLE);
        } else if (bj.isPressed()) {
            spillet.gætBogstav("j");
            bj.setVisibility(View.INVISIBLE);
        } else if (bk.isPressed()) {
            spillet.gætBogstav("k");
            bk.setVisibility(View.INVISIBLE);
        } else if (bl.isPressed()) {
            spillet.gætBogstav("l");
            bl.setVisibility(View.INVISIBLE);
        } else if (bm.isPressed()) {
            spillet.gætBogstav("m");
            bm.setVisibility(View.INVISIBLE);
        } else if (bn.isPressed()) {
            spillet.gætBogstav("n");
            bn.setVisibility(View.INVISIBLE);
        } else if (bo.isPressed()) {
            spillet.gætBogstav("o");
            bo.setVisibility(View.INVISIBLE);
        } else if (bp.isPressed()) {
            spillet.gætBogstav("p");
            bp.setVisibility(View.INVISIBLE);
        } else if (bq.isPressed()) {
            spillet.gætBogstav("q");
            bq.setVisibility(View.INVISIBLE);
        } else if (br.isPressed()) {
            spillet.gætBogstav("r");
            br.setVisibility(View.INVISIBLE);
        } else if (bs.isPressed()) {
            spillet.gætBogstav("s");
            bs.setVisibility(View.INVISIBLE);
        } else if (bt.isPressed()) {
            spillet.gætBogstav("t");
            bt.setVisibility(View.INVISIBLE);
        } else if (bu.isPressed()) {
            spillet.gætBogstav("u");
            bu.setVisibility(View.INVISIBLE);
        } else if (bv.isPressed()) {
            spillet.gætBogstav("v");
            bv.setVisibility(View.INVISIBLE);
        } else if (bw.isPressed()) {
            spillet.gætBogstav("w");
            bw.setVisibility(View.INVISIBLE);
        } else if (bx.isPressed()) {
            spillet.gætBogstav("x");
            bx.setVisibility(View.INVISIBLE);
        } else if (by.isPressed()) {
            spillet.gætBogstav("y");
            by.setVisibility(View.INVISIBLE);
        } else if (bz.isPressed()) {
            spillet.gætBogstav("z");
            bz.setVisibility(View.INVISIBLE);
        } else if (bæ.isPressed()) {
            spillet.gætBogstav("æ");
            bæ.setVisibility(View.INVISIBLE);
        } else if (bø.isPressed()) {
            spillet.gætBogstav("ø");
            bø.setVisibility(View.INVISIBLE);
        } else if (bå.isPressed()) {
            spillet.gætBogstav("å");
            bå.setVisibility(View.INVISIBLE);
        }

        ordet.setText(spillet.getSynligtOrd());
        forkerteGæt = spillet.getAntalForkerteBogstaver();
        switch (forkerteGæt) {
            case 1: galgePic.setImageResource(R.drawable.galge_forkert1); break;
            case 2: galgePic.setImageResource(R.drawable.galge_forkert2); break;
            case 3: galgePic.setImageResource(R.drawable.galge_forkert3); break;
            case 4: galgePic.setImageResource(R.drawable.galge_forkert4); break;
            case 5: galgePic.setImageResource(R.drawable.galge_forkert5); break;
            case 6: galgePic.setImageResource(R.drawable.galge_forkert6); break;
        }

        if (spillet.erSpilletSlut()) {
            if (spillet.erSpilletVundet()) {
            }
            if (spillet.erSpilletTabt()) {
            }
            vedSlut();

        }

    }
}

