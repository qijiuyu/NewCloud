package com.seition.cloud.pro.newcloud.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.bind.BankBean;

import java.util.ArrayList;

public class SelectBankDialog extends Dialog {

    public SelectBankDialog(Context context) {
        super(context);
    }

    public SelectBankDialog(Context context, int theme) {
        super(context, theme);
    }

    public interface bankSelect {
        void selectClick(BankBean bean, int position);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String positiveButtonText;
        private ListView listview;
        private bankSelect bankListener;
        private int selectPosition;
        private OnClickListener positiveButtonClickListener;
        private ArrayList<BankBean> bank;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setList(ArrayList<BankBean> list, bankSelect listener, int selectPosition) {
            this.bank = list;
            this.selectPosition = selectPosition;
            this.bankListener = listener;
            return this;
        }


        public SelectBankDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final SelectBankDialog dialog = new SelectBankDialog(context, R.style.dialog);
            View layout = inflater.inflate(R.layout.dialog_select_bank, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            // set the dialog title
            ((TextView) layout.findViewById(R.id.title)).setText(title);
            // set the confirm button
            if (positiveButtonText != null) {
                ((TextView) layout.findViewById(R.id.positiveButton))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    layout.findViewById(R.id.positive)
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.positiveButton).setVisibility(
                        View.GONE);
            }
            listview = (ListView) layout.findViewById(R.id.listview);
            listview.setAdapter(new BankAdapter());
            dialog.setContentView(layout);
            return dialog;
        }

        class BankAdapter extends BaseAdapter {

            @Override
            public int getCount() {
                if (bank == null) bank = new ArrayList<>();
                return bank.size();
            }

            @Override
            public BankBean getItem(int i) {
                return bank.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(final int i, View view, ViewGroup viewGroup) {
                if (view == null) {
                    view = View.inflate(context, R.layout.item_bank_select, null);
                }
                RadioButton rb = (RadioButton) view.findViewById(R.id.select);
                rb.setText(getItem(i).getCard_info());
                if (selectPosition == i)
                    rb.setChecked(true);
                else rb.setChecked(false);
                rb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (bankListener != null && bank != null)
                            bankListener.selectClick(bank.get(i), i);
                    }
                });
                return view;
            }
        }
    }

}