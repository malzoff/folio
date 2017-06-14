using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class GameController : MonoBehaviour
{
	public static float initTimeLeft = 60;
	private static float timeLeft = initTimeLeft;
	private static float score;
	public Text scoreText;
	public Text timeText;
	public GameObject gameOverText;
	public GameObject gameWinText;

	private static void setText(Text textFieldObj, float number)
	{
		int intNumber = (int)number;
		textFieldObj.text = intNumber.ToString ();
	}

	private static void GameOver(GameObject text)
	{
		text.SetActive (true);
	}

	public static void addTime(int time)
	{
		timeLeft += time;
	}

	public static void addScore(int score)
	{
		GameController.score += score;
	}

	public static void stopTheTime()
	{
		Time.timeScale = 0;
	}

	// Use this for initialization
	void Start()
	{
		score = 0;
		setText (timeText, initTimeLeft);
		setText (scoreText, score);
	}
	
	// Update is called once per frame
	void FixedUpdate()
	{	
		timeLeft -= Time.deltaTime;
		if (timeLeft <= 0 && !Player_Finish.getFinished ()) {
			timeLeft = 0;
			GameOver (gameOverText);
			stopTheTime ();
		}

		if (Player_Finish.getFinished ()) {
			GameOver (gameWinText);
			stopTheTime ();
		}

		if (Player_PickupCoins.coinPicked) {

			setText (scoreText, score);
			Player_PickupCoins.coinPicked = false;
		}

		setText (timeText, timeLeft);
	}
}
