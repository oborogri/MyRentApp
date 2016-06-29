package models;

import java.util.Date;

import javax.persistence.*;
import play.db.jpa.*;

@Entity
public class Message extends Model {
	public String messageText;
	public Date postedAt;

	@ManyToOne
	public User from;
	
	/**
	 * Constructor for message object
	 * 
	 * @param from
	 * @param messageText
	 */

	public Message(User from, String messageText) {
		this.from = from;
		this.messageText = messageText;
		postedAt = new Date();
	}
}
