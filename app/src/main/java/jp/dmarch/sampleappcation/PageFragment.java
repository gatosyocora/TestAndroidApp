package jp.dmarch.sampleappcation;

import android.content.Context;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * http://grandbig.github.io/blog/2016/01/30/android-tablayout/
 */

public class PageFragment extends Fragment {

    private static final String ARG_PARAM = "page";
    private String mParam;
    //private OnFragmentInteractionListener mListener;

    public PageFragment() {
    }

    public static PageFragment newInstance(int page) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        /*if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        int page = getArguments().getInt(ARG_PARAM, 0);
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        ((TextView)view.findViewById(R.id.textView_fragment)).setText("Page" + page);

        return view;
    }

    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener)context;
        } else {
            throw new RuntimeException(context.toString()
                + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }*/

}
