package hu.kata.leszko.tsp.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import hu.kata.leszko.tsp.City;
import hu.kata.leszko.tsp.R;

public class TravellingSalesmanView extends View {
    private Context context = null;
    private Bitmap cityBitmap = null;
    private ArrayList<City> cities;
    private City currentCity = null;
    Bitmap firstCityBitmap = null;

    public TravellingSalesmanView(Context context) {
        super(context);
        this.context = context;
        cities = new ArrayList<>();
        setFocusable(true); //necessary for getting the touch events
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

        boolean isFirst = true;

        //draw the cities on the canvas
        for (City city : cities) {
            if (cityBitmap == null) {
                cityBitmap = getBitmap(R.drawable.uni);
            }
            if(firstCityBitmap == null){
                firstCityBitmap = getBitmap(R.drawable.first_uni);
            }
            if(isFirst){
                canvas.drawBitmap(firstCityBitmap, city.getX(), city.getY(), null);
                isFirst = false;
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
}
