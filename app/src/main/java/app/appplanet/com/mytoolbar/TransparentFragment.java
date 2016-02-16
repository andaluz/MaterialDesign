package app.appplanet.com.mytoolbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * First you have to keep in mind that I have a project with multiple fragments
 * that have the same Toolbar with a fixed background. But for ONE fragment, I
 * want the background transparent so it shows the background of that one fragment.
 *
 * My solution so far:
 * To get a transparent Toolbar, I first hide the toolbar in the MainActivity.
 * Than create a custom toolbar with a transparent background and add a listener
 * on navigation click of the toolbar, which seems to be a built in thing.
 *
 * This is the only solution I found so far that works for me without a lot
 * of coding, xml layout/style editing.
 *
 * What I don't like about this, is I have to hide the other toolbar declared
 * in MainActivity and add a custom one just for this fragment.
 * I rather use the Toolbar in the MainActivity, but making that Toolbar transparent,
 * doesn't show the underlying background of this fragment.
 * If you have a solution to make the Toolbar of the MainActivity transparent, you're
 * welcome to share it with me.
 */
public class TransparentFragment extends Fragment {

    @Nullable
    @Override
    public View  onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transparent_toolbar, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        modifyToolbar();
    }

    private void modifyToolbar() {
        Toolbar toolbar = (Toolbar)((AppCompatActivity) getActivity()).findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);

        final DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);

        Toolbar transparent = (Toolbar) getView().findViewById(R.id.tb_transparent);
        transparent.setNavigationIcon(R.mipmap.ic_menu_white_24dp);
        transparent.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawer!=null) {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();

        Toolbar toolbar = (Toolbar)((AppCompatActivity) getActivity()).findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
    }
}
