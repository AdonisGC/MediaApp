package com.example.adonis.mediaapp;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.clustering.RadiusMarkerClusterer;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;


public class MapFragment extends android.support.v4.app.Fragment {



    private org.osmdroid.views.MapView mvMap;
    private IMapController mapController;
    private MyLocationNewOverlay myLocationOverlay;

    private RadiusMarkerClusterer mediaMarkerClusterer;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        myRef = database.getInstance().getReference().child(((MainActivity) getActivity()).getToken());
        mvMap = (org.osmdroid.views.MapView) view.findViewById(R.id.mvMap);

        initializeMap();
        setZoom();
        putMarkers();
        //Reactualitza el mapa
        //refresh();

        mvMap.invalidate();

        return view;

    }

    private void putMarkers(){

        setupMarkerOverlay();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Gallery gallerypoint = postSnapshot.getValue(Gallery.class);

                    Marker marker = new Marker(mvMap);

                    GeoPoint point = new GeoPoint(gallerypoint.getLatitude(),
                            gallerypoint.getLongitude());

                    marker.setPosition(point);

                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                    marker.setIcon(getResources().getDrawable(R.mipmap.ic_media));
                    marker.setTitle(gallerypoint.getName());
                    marker.setAlpha(0.6f);

                    mediaMarkerClusterer.add(marker);
                }
                mediaMarkerClusterer.invalidate();
                mvMap.invalidate();
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    private void setupMarkerOverlay() {

        mediaMarkerClusterer = new RadiusMarkerClusterer(getContext());
        mvMap.getOverlays().add(mediaMarkerClusterer);

        Drawable clusterIconD = getResources().getDrawable(R.drawable.center);
        Bitmap clusterIcon = ((BitmapDrawable)clusterIconD).getBitmap();
        mediaMarkerClusterer.setIcon(clusterIcon);

        mediaMarkerClusterer.setRadius(100);

    }

    private void initializeMap() {

        mvMap.setTileSource(TileSourceFactory.MAPNIK);
        mvMap.setTilesScaledToDpi(true);
        mvMap.setBuiltInZoomControls(true);
        mvMap.setMultiTouchControls(true);

    }


    private void setZoom() {
        mapController = mvMap.getController();
        mapController.setCenter(new GeoPoint(41.383333, 2.183333));
        mapController.setZoom(12);
    }




}