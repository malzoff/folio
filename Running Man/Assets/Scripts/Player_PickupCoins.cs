using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Player_PickupCoins : MonoBehaviour
{
	public static bool coinPicked = false;



	void OnTriggerEnter(Collider other)
	{
		if (other.gameObject.CompareTag ("Coins")) {
			DestroyObject (other.gameObject);
			GameController.addScore (Coin.addScorePerCoin ());
			coinPicked = true;
			GameController.addTime (Coin.addTimePerCoin ());
		}



	}
}
