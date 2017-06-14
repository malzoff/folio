using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Coin : MonoBehaviour
{

	public static int scorePerCoin = 10;
	public static int timePerCoin = 5;
	//public static int timePerCoin = 5;

	public static int addScorePerCoin()
	{
		return scorePerCoin;
	}

	public static int addTimePerCoin()
	{
		return timePerCoin;
	}
}
