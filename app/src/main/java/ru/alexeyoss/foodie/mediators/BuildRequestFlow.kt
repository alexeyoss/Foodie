@file:Suppress("UNCHECKED_CAST")

package ru.alexeyoss.foodie.mediators

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.alexeyoss.core.common.ConnectionException
import ru.alexeyoss.core.common.Container
import ru.alexeyoss.core.common.RemoteServiceException
import ru.alexeyoss.core.common.UserFriendlyException
import ru.alexeyoss.network.ErrorState
import ru.alexeyoss.network.ResponseStates

fun <NetworkData> buildRequestFlow(
    block: suspend () -> ResponseStates<NetworkData>
): Flow<Container<NetworkData>> {
    return flow {
        emit(Container.Loading)
        val containerState = when (val responseState = block()) {
            is ResponseStates.Success -> Container.Success(responseState.data)
            is ErrorState.ConnectionError -> Container.Error(
                exception = ConnectionException()
            )

            is ResponseStates.Error -> Container.Error(
                exception = UserFriendlyException(
                    userFriendlyMessage = responseState.throwable.message ?: "",
                    cause = responseState.throwable
                )
            )

            is ErrorState.GenericError -> Container.Error(
                exception = UserFriendlyException(
                    userFriendlyMessage = responseState.throwable.message ?: "",
                    cause = responseState.throwable
                )
            )

            is ErrorState.ServerError -> Container.Error(
                exception = RemoteServiceException(
                    message = responseState.message ?: ""

                )
            )
        }
        emit(containerState)
    }
}