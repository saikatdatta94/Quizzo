package com.example.saikat.quizzo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

public class BottomSnackbarClass extends BottomSheetDialogFragment {




    private BottomSheetListener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_snackbar_example,container,false);

        Button btn1 = view.findViewById(R.id.b_sheet_btn1);
        Button btn2 = view.findViewById(R.id.b_sheet_btn2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("Button 1 from bottom sheet");
                dismiss();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("Button 1 from bottom sheet");
                dismiss();
            }
        });

        return view;
    }





    public interface BottomSheetListener{
//        Pass values
        void onButtonClicked(String passText);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    +" must implement BottomSheetListener");
        }

    }

//    Prevent close on touch outside
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View touchOutsideView = getDialog().getWindow()
                .getDecorView()
                .findViewById(android.support.design.R.id.touch_outside);
        touchOutsideView.setOnClickListener(null);
    }


//    Prevent swiping down
    @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog bottomSheetDialog =
                (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        bottomSheetDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override public void onShow(DialogInterface dialog) {
                FrameLayout bottomSheet =
                        bottomSheetDialog.findViewById(android.support.design.R.id.design_bottom_sheet);

                if (null != bottomSheet) {
                    BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
                    behavior.setHideable(false);
                }
            }
        });
        return bottomSheetDialog;
    }
}
