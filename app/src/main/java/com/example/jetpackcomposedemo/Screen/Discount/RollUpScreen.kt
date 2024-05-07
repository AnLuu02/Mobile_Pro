package com.example.jetpackcomposedemo.Screen.Discount

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.Screen.GlobalScreen.AppColor
import com.example.jetpackcomposedemo.Screen.GlobalScreen.LoadingScreen

@Composable
fun smallIconBtn(
  size: Dp = 40.dp,
  imageResId: Int,
  onClickBtn: () -> Unit?
) {
  Box(
    modifier = Modifier
      .size(size)
      .clip(CircleShape)
      .clickable { onClickBtn() },
    contentAlignment = Alignment.Center
  ) {
    Image(
      painter = painterResource(id = imageResId),
      contentDescription = null,
      modifier = Modifier.fillMaxSize(),
      contentScale = ContentScale.Crop
    )
  }
}

@SuppressLint("ResourceType")
@Composable
fun checkDayItem(
  index: Int = 0,
  isChecked: Boolean = false
) {
  val appColor = AppColor()

  Column(
    modifier = Modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Column(
      modifier = Modifier
        .width(45.dp)
        .height(60.dp)
        .padding(bottom = 5.dp)
        .background(if (isChecked) appColor.lightRed else appColor.gray3)
        .border(
          if (isChecked) 1.dp else 0.dp,
          if (isChecked) appColor.red else appColor.gray3,
          shape = RoundedCornerShape(5.dp)
        )
        .clip(CircleShape),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceAround
    ) {
      Text(
        text = "+100",
        style = MaterialTheme.typography.bodySmall,
        fontWeight = FontWeight.Bold
      )
      Box(
        modifier = Modifier
          .size(35.dp)
          .padding(bottom = 5.dp)
          .clip(CircleShape)
          .clickable {

          },
        contentAlignment = Alignment.Center
      ) {
        Image(
          painter = painterResource(id = if (isChecked) R.raw.checked else R.raw.coin),
          contentDescription = null,
          modifier = Modifier.fillMaxSize(),
          contentScale = ContentScale.Crop
        )
      }
    }

    if(isChecked) {
      Text(
        text = getDayText(index),
        style = MaterialTheme.typography.bodySmall,
        fontWeight = FontWeight.SemiBold,
        color = appColor.red
      )
    } else {
      Text(
        text = getDayText(index),
        style = MaterialTheme.typography.bodySmall,
        color = appColor.darkGray
      )
    }
  }
}

@SuppressLint("ResourceType")
@Composable
fun RollUpScreen(
  navController: NavHostController? = null
) {
  val appColor = AppColor()
  var isTodayRolledUp by remember {
    mutableStateOf(false)
  }
  var dayChecking by remember {
    mutableStateOf(mutableListOf(0, 0, 0, 0, 0, 0, 0))
  }
  var totalPoint by remember {
    mutableStateOf(35000)
  }
  var isTurnOnNotificationSuccess by remember {
    mutableStateOf(false)
  }

  var isLoading by remember {
    mutableStateOf(true)
  }


  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(appColor.gray3)
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(1f / 3f)
        .background(appColor.red)
    ) {

    }

    Column (
      modifier = Modifier
        .fillMaxSize()
        .offset(x = 0.dp, y = 0.dp)
        .padding(top = 56.dp)
    ) {
      // top bar
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
      ) {
        smallIconBtn(
          size = 32.dp,
          imageResId = R.raw.back,
          onClickBtn = {
            navController?.navigate("discount")
          }
        )

        Box(
          modifier = Modifier.padding(start = 10.dp)
        ) {
          Text(
            text = "Điểm danh",
            style = MaterialTheme.typography.titleLarge,
            color = appColor.white

          )
        }
      }
      // top bar

      // info point
      Column (
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 10.dp)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(40.dp)
              .clip(CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Image(
              painter = painterResource(id = R.raw.coin_yellow),
              contentDescription = null,
              modifier = Modifier.fillMaxSize(),
              contentScale = ContentScale.Crop
            )
          }
          Box() {
            Text(
              text = "$totalPoint điểm",
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.Bold,
              color = appColor.white
            )
          }
        }

        Row (
          modifier = Modifier.padding(start = 5.dp)
        ) {
          Text(
            text = "Xu sẽ hết hạn vào cuối quý",
            style = MaterialTheme.typography.bodyMedium,
            color = appColor.white
          )
        }
      }
      // info point

      // 7 rec
      Column (
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 5.dp, end = 5.dp, top = 20.dp)
          .background(appColor.white, shape = RoundedCornerShape(3.dp))
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 5.dp),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          repeat(7) { index ->
            // Rectangle (Box) with a specific color
            checkDayItem(
              index = index,
              isChecked = dayChecking[index] == 1
            )
          }
        }
        Button(
          onClick = {
            if(!isTodayRolledUp && !isTurnOnNotificationSuccess) {
              // Handle roll up function
              isTodayRolledUp = true
              dayChecking[0] = 1
              totalPoint += 100
            }
          },
          modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = 20.dp, vertical = 15.dp),
          shape = RoundedCornerShape(100.dp), // Rounded corners
          colors = ButtonDefaults.buttonColors(containerColor = if (isTodayRolledUp) appColor.gray else appColor.red) // Orange color
        ) {
          Text(
            text = "Điểm danh",
            style = MaterialTheme.typography.bodyLarge,
            color = if (isTodayRolledUp) appColor.black else appColor.white
          )
        }
      }
      // 7 rec

      // info
      Box (
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 20.dp)
          .background(appColor.yellow, shape = RoundedCornerShape(3.dp))
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
        ) {
          // Line 1
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .padding(start = 5.dp)
                .size(40.dp)
                .clip(CircleShape),
              contentAlignment = Alignment.Center
            ) {
              Image(
                painter = painterResource(id = R.raw.gift_ver_white),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
              )
            }
            Box(
              modifier = Modifier
                .padding(start = 10.dp)
            ) {
              Text(
                text = "Phần thưởng",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = appColor.white
              )
            }
          }
          // Line 1

          // Line 2
          Column (
            modifier = Modifier
              .padding(horizontal = 10.dp, vertical = 5.dp)
          ) {
            repeat(5) {index ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = ((index + 1) * 10000).toString() + " điểm",
                  style = MaterialTheme.typography.bodyLarge,
                  color = appColor.white
                )
                Button(
                  onClick = {
                    if (((index + 1) * 10000) < totalPoint && totalPoint - ((index + 1) * 10000) >= 0 && !isTurnOnNotificationSuccess) {
                      totalPoint -= ((index + 1) * 10000)
                      isTurnOnNotificationSuccess = true
                    }
                  },
                  modifier = Modifier
                    .width(100.dp)
                    .height(40.dp),
                  shape = RoundedCornerShape(100.dp), // Rounded corners
                  colors = ButtonDefaults.buttonColors(containerColor = if (((index + 1) * 10000) > totalPoint) appColor.gray else appColor.red) // Orange color
                ) {
                  Text(
                    text = "Đổi",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = if (((index + 1) * 10000) > totalPoint) appColor.black else appColor.white
                  )
                }
              }
            }
          }
          // Line 2
        }
      }
     // info
    }
  }

  if(isTurnOnNotificationSuccess) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(appColor.transparentBlack)
        .clickable(
          indication = null,
          interactionSource = remember { MutableInteractionSource() }
        ) {

        },
      contentAlignment = Alignment.Center
    ) {
      Column(
        modifier = Modifier
          .width(200.dp)
          .height(120.dp)
          .background(appColor.red, shape = RoundedCornerShape(5.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Box (
          modifier = Modifier.padding(bottom = 15.dp)
        ) {
          Text(
            text = "Nhận thành công",
            style = MaterialTheme.typography.titleMedium,
            color = appColor.white
          )
        }

        Button(
          onClick = {
            isTurnOnNotificationSuccess = false
          },
          modifier = Modifier
            .width(150.dp)
            .height(40.dp),
          shape = RoundedCornerShape(100.dp), // Rounded corners
          colors = ButtonDefaults.buttonColors(containerColor = appColor.white) // Orange color
        ) {
          Text(
            text = "Biết rồi",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = appColor.red
          )
        }
      }
    }
  }

  if(isLoading) {
    LoadingScreen(isLoadingValue = true)
  }
}


private fun getDayText(index: Int): String {
  return when (index) {
    0 -> "Hôm nay"
    else -> "Ngày ${index + 1}"
  }
}

@Preview
@Composable
fun demoRollUpScreen() {
  RollUpScreen()
}