using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Player_Finish : MonoBehaviour
{
	private static bool isFinished = false;

	public static bool getFinished()
	{
		return isFinished;
	}



	void OnTriggerEnter(Collider other)
	{
		if (other.gameObject.CompareTag ("Finish")) {
			isFinished = true;
		}

	}
}
