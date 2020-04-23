package hu.kata.leszko.tsp.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import hu.kata.leszko.tsp.City;
import hu.kata.leszko.tsp.R;
import hu.kata.leszko.tsp.algorithm.TSPBranchAndBound;
import hu.kata.leszko.tsp.algorithm.TSPBruteForce;
import hu.kata.leszko.tsp.algorithm.TSPGreedy;
import hu.kata.leszko.tsp.algorithm.TSPSolver;

public class TravellingSalesmanView extends View {
    private Context context = null;
    private Bitmap cityBitmap = null;
    private ArrayList<City> cities;
    private City currentCity = null;
    private Bitmap firstCityBitmap = null;
    private Paint linePaint = new Paint();
    private TSPSolver tspSolver;

    public TravellingSalesmanView(Context context) {
        super(context);
        this.context = context;
        cities = new ArrayList<>();
        setFocusable(true); //necessary for getting the touch events
        linePaint = new Paint();
        linePaint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        linePaint.setStrokeWidth(10);
        setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventaction = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        Log.d(TravellingSalesmanView.class.getName(), "onTouchEvent ( " + x + " ; " + y + " )");

        if (cityBitmap == null) {
            cityBitmap = getBitmap(R.drawable.uni);
        }
        int cityWidth = cityBitmap.getWidth();
        int cityheight = cityBitmap.getHeight();

        switch (eventaction) {
            case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on a city
                Log.d(TravellingSalesmanView.class.getName(), "onTouchEvent ACTION_DOWN");

                //Log.d(TravellingSalesmanView.class.getName(), "city bitmap w: " + cityWidth);
                //Log.d(TravellingSalesmanView.class.getName(), "city bitmap h: " + cityheight);

                currentCity = null;

                for (City city : cities) {
                    // check all the bounds of the city
                    if (x > city.getX() && x < city.getX() + cityWidth && y > city.getY() && y < city.getY() + cityheight) {
                        currentCity = city;
                        Log.d(TravellingSalesmanView.class.getName(), "city found");
                        break;
                    }
                }

                //touch is not on a city -> add new city
                if (currentCity == null) {
                    cities.add(new City(x - cityWidth / 2, y - cityheight / 2));
                }

                break;

            case MotionEvent.ACTION_MOVE:   // touch drag with the city
                // move the city the same as the finger
                if (currentCity != null) {
                    Log.d(TravellingSalesmanView.class.getName(), "onTouchEvent ACTION_MOVE");

                    currentCity.setX(x - cityWidth / 2);
                    currentCity.setY(y - cityheight / 2);
                }

                break;

            case MotionEvent.ACTION_UP:
                // touch drop - just do things here after dropping

                break;

        }
        // redraw the canvas
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //draw the route
        if(cities.size() == 2) {
            int cityWidth = cityBitmap.getWidth();
            int cityheight = cityBitmap.getHeight();
            canvas.drawLine(cities.get(0).getX() + cityWidth / 2,
                    cities.get(0).getY() + cityheight / 2,
                    cities.get(1).getX() + cityWidth / 2,
                    cities.get(1).getY() + cityheight / 2,
                    linePaint);
        }
        else if(cities.size() == 3 ){
            int cityWidth = cityBitmap.getWidth();
            int cityheight = cityBitmap.getHeight();
            canvas.drawLine(cities.get(0).getX() + cityWidth / 2,
                    cities.get(0).getY() + cityheight / 2,
                    cities.get(1).getX() + cityWidth / 2,
                    cities.get(1).getY() + cityheight / 2,
                    linePaint);
            canvas.drawLine(cities.get(1).getX() + cityWidth / 2,
                    cities.get(1).getY() + cityheight / 2,
                    cities.get(2).getX() + cityWidth / 2,
                    cities.get(2).getY() + cityheight / 2,
                    linePaint);
            canvas.drawLine(cities.get(2).getX() + cityWidth / 2,
                    cities.get(2).getY() + cityheight / 2,
                    cities.get(0).getX() + cityWidth / 2,
                    cities.get(0).getY() + cityheight / 2,
                    linePaint);
        }

        else if(cities.size() >= 4) {
            int[] order = null;
            try {
                order = tspSolver.solve(cities);
            } catch (Exception e) {
                e.printStackTrace();
            }

            int cityWidth = cityBitmap.getWidth();
            int cityheight = cityBitmap.getHeight();
            for (int i = 0; i < cities.size(); i++) {
                    canvas.drawLine(cities.get(order[i]).getX() + cityWidth / 2,
                            cities.get(order[i]).getY() + cityheight / 2,
                            cities.get(order[i + 1]).getX() + cityWidth / 2,
                            cities.get(order[i + 1]).getY() + cityheight / 2,
                            linePaint);
            }
        }

        //draw the cities on the canvas
        boolean isFirstCity = true;
        for (City city : cities) {
            if (cityBitmap == null) {
                cityBitmap = getBitmap(R.drawable.uni);
            }
            if(firstCityBitmap == null){
                firstCityBitmap = getBitmap(R.drawable.first_uni);
            }
            if(isFirstCity){
                canvas.drawBitmap(firstCityBitmap, city.getX(), city.getY(), null);
                isFirstCity = false;
            } else {
                canvas.drawBitmap(cityBitmap, city.getX(), city.getY(), null);
            }
        }

    }

    public void clearCities(){
        cities.clear();
    }

    private Bitmap getBitmap(int bitmapId) {
        return BitmapFactory.decodeResource(context.getResources(),
                bitmapId);
    }

    public void setTSPAlgorithm(int tspId) {
        switch (tspId){
            case R.id.nav_bruteforce:
                tspSolver = new TSPBruteForce();
                break;
            case R.id.nav_bandb:
                tspSolver = new TSPBranchAndBound();
                break;
            case R.id.nav_greedy:
                tspSolver = new TSPGreedy();
                break;
        }
    }
}
