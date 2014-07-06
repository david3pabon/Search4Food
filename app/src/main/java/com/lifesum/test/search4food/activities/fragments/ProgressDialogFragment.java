package com.lifesum.test.search4food.activities.fragments;



import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.lifesum.test.search4food.R;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ProgressDialogFragment extends DialogFragment {

    public static ProgressDialogFragment newInstance() {
        ProgressDialogFragment frag = new ProgressDialogFragment ();
        return frag;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage(getString(R.string.loading_text));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);

        // Disable the back button
        DialogInterface.OnKeyListener keyListener = new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {

                if( keyCode == KeyEvent.KEYCODE_BACK){
                    return true;
                }
                return false;
            }


        };

        dialog.setOnKeyListener(keyListener);
        return dialog;
    }


}
