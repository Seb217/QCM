package com.gosimpleapp.qcm.client.views.QCM;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gosimpleapp.qcm.client.adaptativeComponent.HTMLAdatativeFontSizeWidget;
import com.gosimpleapp.qcm.client.model.qcm.Proposal;
import com.gosimpleapp.qcm.client.model.qcm.QCMItem;
import com.gosimpleapp.qcm.client.views.edit.HasName;
import com.gosimpleapp.qcm.client.views.edit.Message;

public class QCMItemView extends Composite implements  HasValue<HasName>, ResizeHandler {

	private static QCMItemViewUiBinder uiBinder = GWT
			.create(QCMItemViewUiBinder.class);

	interface QCMItemViewUiBinder extends UiBinder<Widget, QCMItemView> {
	}
	QCMItem qcmItem;

	@UiField HTMLAdatativeFontSizeWidget question;
	@UiField HTML explanation;
	@UiField LayoutPanel layer;
	@UiField HTMLAdatativeFontSizeWidget proposal_0;
	@UiField HTMLAdatativeFontSizeWidget proposal_1;
	@UiField HTMLAdatativeFontSizeWidget proposal_2;
	@UiField HTMLAdatativeFontSizeWidget proposal_3;
	
	

	int nb_clicked=0;
	boolean isHorizontal;
	boolean setAsHorizontal=true;
	
	public QCMItemView() {
		initWidget(uiBinder.createAndBindUi(this));
		Window.addResizeHandler(this);
		setLayers();
		
	
		explanation.setText("");
		setValue(new QCMItem());
	}
	public QCMItemView(QCMItem qcmItem) {
		initWidget(uiBinder.createAndBindUi(this));

		Window.addResizeHandler(this);
		setLayers();
		explanation.setText("");
		setValue(qcmItem);
		
	}
	@Override
	public void onResize(ResizeEvent event) {
		 Scheduler.get().scheduleDeferred(
	                new Scheduler.ScheduledCommand() {
	                    public void execute() {
	                    	setLayers();
	                    }
	                });  
		
	}
	void setLayers(){
		
		System.out.println("setLayers");
		isHorizontal=Window.getClientWidth()>=Window.getClientHeight();
		if (isHorizontal && ! setAsHorizontal){
			System.out.println("to horizontal");
			layer.setWidgetTopHeight(layer.getWidget(0), 1,Unit.PCT, 48, Unit.PCT);
			layer.setWidgetLeftWidth(layer.getWidget(0), 1,Unit.PCT, 48, Unit.PCT);
			
			layer.setWidgetTopHeight(layer.getWidget(1), 1,Unit.PCT, 48, Unit.PCT);
			layer.setWidgetLeftWidth(layer.getWidget(1), 51,Unit.PCT, 48, Unit.PCT);
			
			//
			
			layer.setWidgetTopHeight(layer.getWidget(2), 51,Unit.PCT, 48, Unit.PCT);
			layer.setWidgetLeftWidth(layer.getWidget(2), 1,Unit.PCT, 48, Unit.PCT);
			
			layer.setWidgetTopHeight(layer.getWidget(3), 51,Unit.PCT, 48, Unit.PCT);
			layer.setWidgetLeftWidth(layer.getWidget(3), 51,Unit.PCT, 48, Unit.PCT);
			
			setAsHorizontal=true;
			
		}else if (!isHorizontal &&  setAsHorizontal){
			
			System.out.println("to vertical");
			layer.setWidgetTopHeight(layer.getWidget(0), 1,Unit.PCT, 23, Unit.PCT);
			layer.setWidgetTopHeight(layer.getWidget(1), 26,Unit.PCT, 23, Unit.PCT);
			layer.setWidgetTopHeight(layer.getWidget(2), 51,Unit.PCT, 23, Unit.PCT);
			layer.setWidgetTopHeight(layer.getWidget(3), 76,Unit.PCT, 23, Unit.PCT);
			
			
			layer.setWidgetLeftWidth(layer.getWidget(0), 1,Unit.PCT, 99, Unit.PCT);
			layer.setWidgetLeftWidth(layer.getWidget(1), 1,Unit.PCT, 99, Unit.PCT);
			layer.setWidgetLeftWidth(layer.getWidget(2), 1,Unit.PCT, 99, Unit.PCT);
			layer.setWidgetLeftWidth(layer.getWidget(3), 1,Unit.PCT, 99, Unit.PCT);
			setAsHorizontal=false;
			
		}
	}
	
	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<HasName> handler) {
		return addHandler(handler,ValueChangeEvent.getType());
	}

	@Override
	public HasName getValue() {
		return qcmItem;
	}

	@Override
	public void setValue(HasName value) {
		qcmItem =(QCMItem) value;
		question.setText(qcmItem.question);
		proposal_0.setText(qcmItem.proposal_0.statement);
		proposal_1.setText(qcmItem.proposal_1.statement);
		proposal_2.setText(qcmItem.proposal_2.statement);
		proposal_3.setText(qcmItem.proposal_3.statement);
		explanation.setText("");

		
		proposal_0.getElement().getStyle().clearBackgroundColor();
		proposal_1.getElement().getStyle().clearBackgroundColor();
		proposal_2.getElement().getStyle().clearBackgroundColor();
		proposal_3.getElement().getStyle().clearBackgroundColor();
		
	}

	@Override
	public void setValue(HasName value, boolean fireEvents) {
		setValue(value);
		if (fireEvents){
			ValueChangeEvent.fire(this, getValue());
		}
		
	}

	
	void manageClick(Widget proposal_html,Proposal propsal){
		nb_clicked++;
		boolean responseIsGood=propsal.answer.isASolution;
		if (responseIsGood){
			System.out.println("Réponse correcte");
	
		}else{

			System.out.println("Réponse incorrecte");
		}
		if (propsal.answer.isASolution){
			proposal_html.getElement().getStyle().setBackgroundColor("#8CA176");
			
		}else{
			proposal_html.getElement().getStyle().setBackgroundColor("#644F65");
		}


			
		explanation.setHTML(propsal.answer.explanation);
		
		if (responseIsGood){
			ValueChangeEvent.fire(this, new Message(Message.GOOD));
		}else{
			ValueChangeEvent.fire(this, new Message(Message.BAD));
		}
		
	}
	

	@UiHandler("next")
	void onNextClick(ClickEvent event) {
		if (nb_clicked>=1){
			ValueChangeEvent.fire(this,(HasName) new Message(Message.NEW));
		}
	}

	@UiHandler("proposal_0")
	void onProposal_0Click(ClickEvent event) {
		manageClick(proposal_0,qcmItem.proposal_0);
	}

	@UiHandler("proposal_1")
	void onProposal_1Click(ClickEvent event) {
		manageClick(proposal_1,qcmItem.proposal_1);
	}
	@UiHandler("proposal_2")
	void onProposal_2Click(ClickEvent event) {
		manageClick(proposal_2,qcmItem.proposal_2);
	}
	@UiHandler("proposal_3")
	void onProposal_3Click(ClickEvent event) {
		manageClick(proposal_3,qcmItem.proposal_3);
	}

}
