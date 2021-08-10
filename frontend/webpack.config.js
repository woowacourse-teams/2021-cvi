const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const webpack = require('webpack');

const isProduction = process.env.NODE_ENV == 'production';
const dotenv = require('dotenv').config();

const config = {
  entry: './src/index.js',
  output: {
    path: path.resolve(__dirname, 'dist'),
    publicPath: '/',
  },
  devServer: {
    hot: true,
    open: true,
    host: 'localhost',
    compress: true,
    historyApiFallback: true,
    port: 9000,
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: './public/index.html',
    }),
    new webpack.EnvironmentPlugin(Object.keys(dotenv.parsed || {})),
  ],
  module: {
    rules: [
      {
        test: /\.(js|jsx)$/i,
        loader: 'babel-loader',
        exclude: /node_modules/,
        options: {
          presets: ['@babel/preset-react', '@babel/preset-env'],
          plugins: ['@babel/plugin-transform-runtime', 'react-require'],
          sourceType: 'unambiguous',
        },
      },
      {
        test: /\.(eot|ttf|woff|woff2|png|jpg|gif)$/i,
        type: 'asset',
      },
      {
        test: /\.svg$/,
        use: ['@svgr/webpack'],
      },
      {
        test: /\.css$/,
        use: ['css-loader'],
      },
    ],
  },
  resolve: {
    modules: [path.resolve(__dirname, 'src'), 'node_modules'],
    extensions: ['.js', '.jsx'],
  },
};

module.exports = () => {
  if (isProduction) {
    config.mode = 'production';
  } else {
    config.mode = 'development';
  }
  return config;
};
