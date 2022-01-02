package eu.spigotui.ui;

public enum ClickAction {
	LEFT, LEFT_SHIFT, RIGHT, RIGHT_SHIFT;

	public static ClickAction getFromStates(boolean isLeftClick, boolean isRightClick, boolean isShiftClick) {
		if (isLeftClick && isShiftClick)
			return ClickAction.LEFT_SHIFT;
		if (isLeftClick && !isShiftClick)
			return ClickAction.LEFT;
		if (isRightClick && isShiftClick)
			return ClickAction.RIGHT_SHIFT;
		if (isRightClick && !isShiftClick)
			return ClickAction.RIGHT;
		return ClickAction.LEFT;
	}

}
