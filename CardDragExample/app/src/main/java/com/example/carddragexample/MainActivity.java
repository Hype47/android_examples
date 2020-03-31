package com.example.carddragexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;

import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {

    private MaterialCardView card;

    public String  getDemoTitleResId() {
        return "Drag Card Demo";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DraggableCoordinatorLayout container = new DraggableCoordinatorLayout;

        card = findViewById(R.id.draggable_card);
        card.setAccessibilityDelegate(cardDelegate);
        container.addDraggableChild(card);

        container.setViewDragListener(
                new ViewDragListener() {
                    @Override
                    public void onViewCaptured(@NonNull View view, int i) {
                        card.setDragged(true);
                    }

                    @Override
                    public void onViewReleased(@NonNull View view, float v, float v1) {
                        card.setDragged(false);
                    }
                });
    }

    private final View.AccessibilityDelegate cardDelegate = new View.AccessibilityDelegate() {
        @Override
        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                return;
            }

            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) card
                    .getLayoutParams();
            int gravity = layoutParams.gravity;
            boolean isOnLeft = (gravity & Gravity.LEFT) == Gravity.LEFT;
            boolean isOnRight = (gravity & Gravity.RIGHT) == Gravity.RIGHT;
            boolean isOnTop = (gravity & Gravity.TOP) == Gravity.TOP;
            boolean isOnBottom = (gravity & Gravity.BOTTOM) == Gravity.BOTTOM;
            boolean isOnCenter = (gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.CENTER_HORIZONTAL;

            /*if (!(isOnTop && isOnLeft)) {
                info.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.move_card_top_left_action,
                        getString(R.string.cat_card_action_move_top_left)));
            }
            if (!(isOnTop && isOnRight)) {
                info.addAction(new AccessibilityAction(R.id.move_card_top_right_action,
                        getString(R.string.cat_card_action_move_top_right)));
            }
            if (!(isOnBottom && isOnLeft)) {
                info.addAction(new AccessibilityAction(R.id.move_card_bottom_left_action,
                        getString(R.string.cat_card_action_move_bottom_left)));
            }
            if (!(isOnBottom && isOnRight)) {
                info.addAction(new AccessibilityAction(
                        R.id.move_card_bottom_right_action,
                        getString(R.string.cat_card_action_move_bottom_right)));
            }
            if (!isOnCenter) {
                info.addAction(new AccessibilityAction(
                        R.id.move_card_center_action,
                        getString(R.string.cat_card_action_move_center)));
            }*/
        }

        @Override
        public boolean performAccessibilityAction(View host, int action, Bundle arguments) {
            int gravity;
            if (action == R.id.move_card_top_left_action) {
                gravity = Gravity.TOP | Gravity.LEFT;
            } else if (action == R.id.move_card_top_right_action) {
                gravity = Gravity.TOP | Gravity.RIGHT;
            } else if (action == R.id.move_card_bottom_left_action) {
                gravity = Gravity.BOTTOM | Gravity.LEFT;
            } else if (action == R.id.move_card_bottom_right_action) {
                gravity = Gravity.BOTTOM | Gravity.RIGHT;
            } else if (action == R.id.move_card_center_action) {
                gravity = Gravity.CENTER;
            } else {
                return super.performAccessibilityAction(host, action, arguments);
            }

            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) card
                    .getLayoutParams();
            if (layoutParams.gravity != gravity) {
                layoutParams.gravity = gravity;
                card.requestLayout();
            }

            return true;
        }
    };


}
