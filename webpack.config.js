const path = require('path');
const TerserPlugin = require('terser-webpack-plugin');
const CssMinimizerPlugin = require('css-minimizer-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const WarningsToErrorsPlugin = require('warnings-to-errors-webpack-plugin');
const TsconfigPathsPlugin = require('tsconfig-paths-webpack-plugin');


module.exports = (env, argv) => ({
  entry: 'ts/app.ts',
  output: {
    path: path.resolve(__dirname, './target/classes/static'),
    filename: 'js/bundle.js'
  },
  devtool: argv.mode === 'production' ? false : 'eval-source-map',
  performance: {
    maxEntrypointSize: 488000,
    maxAssetSize: 488000
  },
  optimization: {
    minimize: true,
    minimizer: [
      new TerserPlugin(),
      new CssMinimizerPlugin()
    ]
  },
  plugins: [
    new MiniCssExtractPlugin({
      filename: "css/bundle.css"
    }),
    new WarningsToErrorsPlugin()
  ],
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        use: ['ts-loader']
      },
      {
        test: /\.scss$/,
        include: path.resolve(__dirname, './src/main/resources/scss'),
        use: [
          argv.mode === 'production' ? MiniCssExtractPlugin.loader : 'style-loader',
          {
            loader: 'css-loader',
            options: {
              importLoaders: 1,
              sourceMap: true
            }
          },
          {
            loader: 'postcss-loader',
            options: {
              postcssOptions: {
                plugins: [
                  require('autoprefixer'),
                ]
              },
              sourceMap: true
            }
          },
          {
            loader: 'sass-loader',
            options: { sourceMap: true }
          }
        ]
      }
    ]
  },
  resolve: {
    plugins: [new TsconfigPathsPlugin({})],
  },
  devServer: {
    port: 8081,
    compress: true,
    watchFiles: [
      'src/main/resources/templates/**/*.html',
      'src/main/resources/ts/**/*.ts',
      'src/main/resources/scss/**/*.scss'
    ],
    proxy: [
      {
        context: '**',
        target: 'http://localhost:8080',
        secure: false,
        prependPath: false,
        headers: {
          'X-Devserver': '1',
        }
      }
    ]
  }
});
