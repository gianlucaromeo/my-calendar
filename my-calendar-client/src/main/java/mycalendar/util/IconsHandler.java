package mycalendar.util;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import mycalendar.config.ColorsSettings;
import mycalendar.controller.ScenesController;

public class IconsHandler {
	
	private final static String INIT_CALENDAR_SIZE = "251px";
	private final static String INIT_CALENDAR_LIGHT = ColorsSettings.MY_CAL_BLUE;
	private final static String INIT_CALENDAR_DARK = ColorsSettings.WHITE;
	public static Text INIT_CALENDAR() {
		Text INIT_CALENDAR = GlyphsDude.createIcon(FontAwesomeIcon.CALENDAR, INIT_CALENDAR_SIZE);
		String col = ScenesController.getCurrentTheme() == ScenesController.DARK_THEME ? INIT_CALENDAR_DARK : INIT_CALENDAR_LIGHT;
		INIT_CALENDAR.setFill(Paint.valueOf(col));
		return INIT_CALENDAR;
	} 
	
	private final static String CALENDAR_TOP_BAR_SIZE = "15px";
	private final static String CALENDAR_TOP_LIGHT = ColorsSettings.WHITE;
	private final static String CALENDAR_TOP_DARK = ColorsSettings.WHITE;
	public static Text CALENDAR_TOP_BAR() {
		Text CALENDAR_TOP_BAR = GlyphsDude.createIcon(FontAwesomeIcon.CALENDAR, CALENDAR_TOP_BAR_SIZE);
		String col = ScenesController.getCurrentTheme() == ScenesController.DARK_THEME ? CALENDAR_TOP_DARK : CALENDAR_TOP_LIGHT;
		CALENDAR_TOP_BAR.setFill(Paint.valueOf(col));
		return CALENDAR_TOP_BAR;
	}
	
	private final static String SELECTED_EV_CALENDAR_SIZE = "21px";
	private final static String SELECTED_EV_CALENDAR_COLOR = ColorsSettings.WHITE;
	public static Text SELECTED_EV_CALENDAR() {
		Text SELECTED_EV_CALENDAR = GlyphsDude.createIcon(FontAwesomeIcon.CALENDAR, SELECTED_EV_CALENDAR_SIZE);
		SELECTED_EV_CALENDAR.setFill(Paint.valueOf(SELECTED_EV_CALENDAR_COLOR));
		return SELECTED_EV_CALENDAR;
	}
	
	private final static String MAP_MARKER_SIZE = "21px";
	private final static String MAP_MARKER_DARK = ColorsSettings.WHITE;
	private final static String MAP_MARKER_LIGHT = ColorsSettings.BLACK;
	public static Text MAP_MARKER() {
		Text MAP_MARKER = GlyphsDude.createIcon(FontAwesomeIcon.MAP_MARKER, MAP_MARKER_SIZE);
		String col = ScenesController.getCurrentTheme() == ScenesController.DARK_THEME ? MAP_MARKER_DARK : MAP_MARKER_LIGHT;
		MAP_MARKER.setFill(Paint.valueOf(col));
		return MAP_MARKER;
	}
	
	private final static String SMALL_MAP_MARKER_SIZE = "9px";
	public static Text SMALL_MAP_MARKER() {
		Text SMALL_MARKER = GlyphsDude.createIcon(FontAwesomeIcon.MAP_MARKER, SMALL_MAP_MARKER_SIZE);
		String col = ScenesController.getCurrentTheme() == ScenesController.DARK_THEME ? MAP_MARKER_DARK : MAP_MARKER_LIGHT;
		SMALL_MARKER.setFill(Paint.valueOf(col));
		return SMALL_MARKER;
	}
	

	private final static String DESCR_SIZE = "21px";
	private final static String LIGHT_DESCR_COLOR = ColorsSettings.BLACK;
	private final static String DARK_DESCR_COLOR = ColorsSettings.WHITE;
	public static Text DESCRIPTION() {
		Text DESCRIPTION = GlyphsDude.createIcon(FontAwesomeIcon.ALIGN_RIGHT, DESCR_SIZE);
		String col = ScenesController.getCurrentTheme() == ScenesController.DARK_THEME ? DARK_DESCR_COLOR : LIGHT_DESCR_COLOR;
		DESCRIPTION.setFill(Paint.valueOf(col));
		return DESCRIPTION;
	}
	
	private final static String SMALL_DESCR_SIZE = "9px";
	public static Text SMALL_DESCRIPTION() {
		Text SMALL_DESCRIPTION = GlyphsDude.createIcon(FontAwesomeIcon.ALIGN_RIGHT, SMALL_DESCR_SIZE);
		String col = ScenesController.getCurrentTheme() == ScenesController.DARK_THEME ? DARK_DESCR_COLOR : LIGHT_DESCR_COLOR;
		SMALL_DESCRIPTION.setFill(Paint.valueOf(col));
		return SMALL_DESCRIPTION;
	}
	
	
	private final static String CLOCK_SIZE = "21px";
	private final static String LIGHT_CLOCK_COLOR = ColorsSettings.BLACK;
	private final static String DARK_CLOCK_COLOR = ColorsSettings.WHITE;
	public static Text CLOCK() {
		Text CLOCK = GlyphsDude.createIcon(FontAwesomeIcon.CLOCK_ALT, CLOCK_SIZE);
		String col = ScenesController.getCurrentTheme() == ScenesController.DARK_THEME ? DARK_CLOCK_COLOR : LIGHT_CLOCK_COLOR;
		CLOCK.setFill(Paint.valueOf(col));
		return CLOCK;
	}
	
	private final static String SMALL_CLOCK_SIZE = "9px";
	public static Text SMALL_CLOCK() {
		Text SMALL_CLOCK = GlyphsDude.createIcon(FontAwesomeIcon.CLOCK_ALT, SMALL_CLOCK_SIZE);
		String col = ScenesController.getCurrentTheme() == ScenesController.DARK_THEME ? DARK_CLOCK_COLOR : LIGHT_CLOCK_COLOR;
		SMALL_CLOCK.setFill(Paint.valueOf(col));
		return SMALL_CLOCK;
	}
	
	private final static String THEME_SIZE = "18px";
	private final static String THEME_COLOR = "#000000";
	public static Text THEME() {
		Text THEME = GlyphsDude.createIcon(FontAwesomeIcon.ADJUST, THEME_SIZE);
		THEME.setFill(Paint.valueOf(THEME_COLOR));
		return THEME;
	}
	
	
	private final static String CLOSE_DIALOG_SIZE = "20px";
	private final static String CLOSE_DIALOG_COLOR = "#FEFEFE";
	public static Text CLOSE_DIALOG() {
		Text CLOSE_DIALOG = GlyphsDude.createIcon(FontAwesomeIcon.CLOSE, CLOSE_DIALOG_SIZE);
		CLOSE_DIALOG.setFill(Paint.valueOf(CLOSE_DIALOG_COLOR));
		return CLOSE_DIALOG;
	}
	
	private final static String SMALL_CLOSE_DIALOG_SIZE = "17px";
	private final static String SMALL_CLOSE_DIALOG_COLOR = ColorsSettings.ICONS_RED;
	public static Text SMALL_CLOSE_DIALOG() {
		Text SMALL_CLOSE_DIALOG = GlyphsDude.createIcon(FontAwesomeIcon.CLOSE, SMALL_CLOSE_DIALOG_SIZE);
		SMALL_CLOSE_DIALOG.setFill(Paint.valueOf(SMALL_CLOSE_DIALOG_COLOR));
		return SMALL_CLOSE_DIALOG;
	}
	
	private final static String GO_BACK_SIZE = "24px";
	private final static String GO_BACK_COLOR = ColorsSettings.WHITE;
	public static Text  GO_BACK(){
		Text GO_BACK = GlyphsDude.createIcon(FontAwesomeIcon.ARROW_LEFT, GO_BACK_SIZE);
		GO_BACK.setFill(Paint.valueOf(GO_BACK_COLOR));
		return GO_BACK;
	}
	
	private final static String SMALL_PLUS_CIRCLE_COLOR = ColorsSettings.WHITE;
	private final static String SMALL_PLUS_CIRCLE_SIZE = "26px";
	public static Text SMALL_PLUS_CIRCLE() {
		Text SMALL_PLUS_CIRCLE = GlyphsDude.createIcon(FontAwesomeIcon.PLUS_CIRCLE, SMALL_PLUS_CIRCLE_SIZE);
		SMALL_PLUS_CIRCLE.setFill(Paint.valueOf(SMALL_PLUS_CIRCLE_COLOR));
		return SMALL_PLUS_CIRCLE;
	}
	
	private final static String BIG_PLUS_CIRCLE_COLOR = ColorsSettings.WHITE; //"linear-gradient(to bottom right, #3c8cfa -1%, #66ffcc 105%)";
	private final static String BIG_PLUS_CIRCLE_SIZE = "28px";
	public static Text BIG_PLUS_CIRCLE() {
		Text SMALL_PLUS_CIRCLE = GlyphsDude.createIcon(FontAwesomeIcon.PLUS_CIRCLE, BIG_PLUS_CIRCLE_SIZE);
		SMALL_PLUS_CIRCLE.setFill(Paint.valueOf(BIG_PLUS_CIRCLE_COLOR));
		return SMALL_PLUS_CIRCLE;
	}
	
	private final static String LIGHT_NO_EVENTS = ColorsSettings.MY_CAL_BLUE;
	private final static String DARK_NO_EVENTS = ColorsSettings.WHITE;
	private final static String NO_EVENTS_SIZE = "28px";
	public static Text NO_EVENTS() {
		Text NO_EVENTS = GlyphsDude.createIcon(FontAwesomeIcon.CALENDAR_TIMES_ALT, NO_EVENTS_SIZE);
		String col = ScenesController.getCurrentTheme() == ScenesController.DARK_THEME ? DARK_NO_EVENTS : LIGHT_NO_EVENTS;
		NO_EVENTS.setFill(Paint.valueOf(col));
		return NO_EVENTS;
	}
	
	private final static String QUESTION_CIRCLE_COLOR = ColorsSettings.BORDER_COLOR;
	private final static String QUESTION_CIRCLE_SIZE = "17px";
	public static Text QUESTION_CIRCLE() {
		Text QUESTION_CIRCLE = GlyphsDude.createIcon(FontAwesomeIcon.QUESTION_CIRCLE, QUESTION_CIRCLE_SIZE);
		QUESTION_CIRCLE.setFill(Paint.valueOf(QUESTION_CIRCLE_COLOR));
		return QUESTION_CIRCLE;
	}

	private final static String PENCIL_COLOR = ColorsSettings.BLACK;
	private final static String PENCIL_SIZE = "11px";
	public static Text PENCIL() {
		Text PENCIL = GlyphsDude.createIcon(FontAwesomeIcon.PENCIL, PENCIL_SIZE);
		PENCIL.setFill(Paint.valueOf(PENCIL_COLOR));
		return PENCIL;
	}
	
	private final static String TRASH_COLOR = ColorsSettings.ICONS_RED;
	private final static String TRASH_SIZE = "11px";
	public static Text TRASH() {
		Text TRASH = GlyphsDude.createIcon(FontAwesomeIcon.TRASH, TRASH_SIZE);
		TRASH.setFill(Paint.valueOf(TRASH_COLOR));
		return TRASH;
	}
	
	private final static String MINUS_COLOR = ColorsSettings.BORDER_COLOR;
	private final static String MINUS_SIZE = "10px";
	public static Text MINUS() {
		Text MINUS = GlyphsDude.createIcon(FontAwesomeIcon.MINUS, MINUS_SIZE);
		MINUS.setFill(Paint.valueOf(MINUS_COLOR));
		return MINUS;
	}
	
	private final static String GEAR_COLOR = ColorsSettings.WHITE;
	private final static String GEAR_SIZE = "15px";
	public static Text GEAR() {
		Text GEAR = GlyphsDude.createIcon(FontAwesomeIcon.GEAR, GEAR_SIZE);
		GEAR.setFill(Paint.valueOf(GEAR_COLOR));
		return GEAR;
	}
	
	private final static String NOTIF_USER_SIZE = "27px";
	public static Text NOTIF_USER() {
		Text NOTIF_USER = GlyphsDude.createIcon(FontAwesomeIcon.USER, NOTIF_USER_SIZE);
		return NOTIF_USER;
	}
	
	private final static String NOTIF_EVENT_SIZE = "27px";
	public static Text NOTIF_EVENT() {
		Text NOTIF_EVENT = GlyphsDude.createIcon(FontAwesomeIcon.EXCLAMATION, NOTIF_EVENT_SIZE);
		return NOTIF_EVENT;
	}
	
}
