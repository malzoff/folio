using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Coin_Rotation : MonoBehaviour
{
	public int speed = 180;
	// Update is called once per frame
	void Update()
	{
		transform.Rotate (speed * Vector3.down * Time.deltaTime, Space.Self);
	}
}
