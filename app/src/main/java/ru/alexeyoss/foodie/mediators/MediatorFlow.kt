package ru.alexeyoss.foodie.mediators

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.alexeyoss.foodie.core.common.data.Container
import ru.alexeyoss.foodie.core.common.exceptions.CommonException
import ru.alexeyoss.foodie.core.common.exceptions.ConnectionException
import ru.alexeyoss.foodie.services.network.utils.ErrorState
import ru.alexeyoss.foodie.services.network.utils.ResponseStates

/**
 * Flow wrapper for matching [ResponseStates] with [Container] states
 * */
fun <NetworkData> buildNetworkFlow(
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
                exception = CommonException(
                    message = responseState.throwable.message ?: ""
                )
            )

            is ErrorState.GenericError -> Container.Error(
                exception = CommonException(
                    message = responseState.throwable.message ?: ""
                )
            )

            is ErrorState.ServerError -> Container.Error(
                exception = ConnectionException()
            )
        }
        emit(containerState)
    }
}