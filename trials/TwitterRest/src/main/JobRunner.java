package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Properties;

import models.UrlDTO;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.HttpException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONException;

import services.twitter.TwitterServices;

/**
 * This class runs the job
 * @author nischalhp
 *
 */

public class JobRunner implements Runnable {

	private static String propertiesMain = "properties/property.properties";
	private UrlDTO urlObj;

	JobRunner(UrlDTO urlObj) {

		this.urlObj = urlObj;
	}

	@Override
	public void run() {

		Logger log = null;
		Properties propertyHandler = new Properties();
		try {
			propertyHandler.load(new FileInputStream(propertiesMain));
			String logPath = propertyHandler.getProperty("logPath");
			PropertyConfigurator.configure(new FileInputStream(logPath));
			log = Logger.getLogger(JobRunner.class.getName());
			//log.info("Logger has been set , now firing call to get tweets");
			oauth.signpost.OAuthConsumer consumerObj= TweetsDownloader.getConsumerObject();
			TwitterServices.getTweets(consumerObj, urlObj);
			TweetsDownloader.putConsumerObject(consumerObj);

		} catch (FileNotFoundException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} catch (OAuthMessageSignerException e) {
			log.error(e);
		} catch (OAuthExpectationFailedException e) {
			log.error(e);
		} catch (OAuthCommunicationException e) {
			log.error(e);
		} catch (IllegalStateException e) {
			log.error(e);
		} catch (JSONException e) {
			log.error(e);
		} catch (URISyntaxException e) {
			log.error(e);
		} catch (SQLException e) {
			log.error(e);
		} catch (HttpException e) {
			log.error(e);
		} catch (InterruptedException e){
			log.error(e);
		}

	}

}
