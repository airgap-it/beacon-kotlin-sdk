package it.airgap.beaconsdk.core.internal.annotation

@RequiresOptIn(
    level = RequiresOptIn.Level.ERROR,
    message = "This is an internal it.airgap.beaconsdk API that should not be used outside of it.airgap.beaconsdk."
)
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.TYPEALIAS, AnnotationTarget.PROPERTY, AnnotationTarget.CONSTRUCTOR)
public annotation class InternalBeaconApi()
