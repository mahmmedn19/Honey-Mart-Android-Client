package org.the_chance.honeymart.ui.features.orders.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.features.orders.toDateFormat
import org.the_chance.honeymart.ui.features.orders.toPriceFormat
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100
import org.the_chance.honymart.ui.theme.white

@Composable
fun ItemOrder(
    onClickCard: (orderId: Long) -> Unit,
    orderId: Long,
    userName: String,
    price: String,
    time: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    val selectedColor by animateColorAsState(
        targetValue = if (isSelected) primary100 else Color.Transparent,
        label = "Selected color"
    )

    Card(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .height(MaterialTheme.dimens.itemOrder)
            .clickable { onClickCard(orderId) },
        colors = CardDefaults.cardColors(containerColor = white)
    ) {
        Row {
            Box(
                modifier = Modifier
                    .background(color = selectedColor)
                    .fillMaxHeight()
                    .width(MaterialTheme.dimens.space8)
            )
            Column(
                modifier = Modifier.padding(
                    horizontal = MaterialTheme.dimens.space24,
                    vertical = MaterialTheme.dimens.space8
                ),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.order, orderId),
                        color = black60,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Box(
                        modifier = Modifier
                            .background(color = selectedColor, shape = CircleShape)
                            .size(MaterialTheme.dimens.icon14)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_person),
                            contentDescription = "userName",
                            tint = black37,
                        )
                        Text(
                            text = userName,
                            color = black37,
                            style = MaterialTheme.typography.displayLarge
                        )
                    }
                    Text(
                        text = price,
                        color = black60,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_clock),
                        contentDescription = "time",
                        tint = black37,
                    )
                    Text(
                        text = time,
                        color = black37,
                        style = MaterialTheme.typography.displayLarge
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewItemOrder() {
    HoneyMartTheme {
        Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space24)) {
            ItemOrder(
                onClickCard = {},
                orderId = 4,
                userName = "Mohamed Shaban",
                price = 9999.0.toPriceFormat(),
                time = 1692699751209.toDateFormat(),
                isSelected = true
            )
            ItemOrder(
                onClickCard = {},
                orderId = 4,
                userName = "Mohamed Shaban",
                price = 9999.0.toPriceFormat(),
                time = 1692699751209.toDateFormat(),
                isSelected = false
            )
        }
    }
}