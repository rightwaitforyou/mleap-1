package ml.combust.mleap.runtime.transformer.regression

import ml.combust.mleap.core.regression.RandomForestRegressionModel
import ml.combust.mleap.runtime.function.UserDefinedFunction
import ml.combust.mleap.runtime.transformer.Transformer
import ml.combust.mleap.runtime.transformer.builder.TransformBuilder
import ml.combust.mleap.tensor.Tensor
import ml.combust.mleap.core.util.VectorConverters._
import ml.combust.mleap.runtime.types.{DoubleType, StructField, TensorType}

import scala.util.{Success, Try}

/**
  * Created by hwilkins on 11/8/15.
  */
case class RandomForestRegression(uid: String = Transformer.uniqueName("random_forest_regression"),
                                  featuresCol: String,
                                  predictionCol: String,
                                  model: RandomForestRegressionModel) extends Transformer {
  val exec: UserDefinedFunction = (features: Tensor[Double]) => model(features)

  override def transform[TB <: TransformBuilder[TB]](builder: TB): Try[TB] = {
    builder.withOutput(predictionCol, featuresCol)(exec)
  }

  override def getFields(): Try[Seq[StructField]] = Success(
    Seq(StructField(featuresCol, TensorType(DoubleType())),
      StructField(predictionCol, DoubleType())))
}
